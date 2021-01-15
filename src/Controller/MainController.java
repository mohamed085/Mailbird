package Controller;

import Model.*;
import Services.*;
import View.ViewFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class MainController extends AbstractController implements Initializable{
	
    public MainController(ModelAccess modelAccess) {
		super(modelAccess);
	}
	private Stage stage;
	private ViewFactory viewFactory;
	private EmailMessageBean selectedMessage;
	private EmailAccountBean emailAccountBean;
	private SeenMessageService seenMessageService;
	private EmailAccountFactory emailAccountFactory;
	private EmailAccountServices emailAccountServices;
	private FolderUpdateServices folderUpdateServices;
	private DeleteMessageService deleteMessageService;
	private MessageRenderServices messageRenderServices;
	private SaveAttachmentServices saveAttachmentServices;
	private EmailAccountFoldersServices emailAccountFoldersServices;

	@FXML
	private AnchorPane mainAnchorPane;

	@FXML
	private TreeView<String> emailFolders;
	private MenuItem showDetails = new MenuItem("Show details");
	private MenuItem deleteMessage = new MenuItem("Delete message");
	private ContextMenu contextMenu = new ContextMenu();
	private TreeItem<String> root  = new TreeItem<String>();

	@FXML
	private TableView<EmailMessageBean> mailTableView;

	@FXML
	private TableColumn<EmailMessageBean, String> fromCol,subjectCol,dateSentCol,sizeCol;

	@FXML
	private TableColumn<EmailMessageBean, Button> markStarredCol , markAttachmentCol;

	@FXML
	private WebView messageRender;

	@FXML
	private ProgressBar downloadAttachmentProgressBar;

	@FXML
	private Label downloadAttachmentLabel;

	@FXML
	private Button downloadAttachmentBtn;

	@FXML
	void downloadAttachmentBtnAction(ActionEvent event) {
		EmailMessageBean message = mailTableView.getSelectionModel().getSelectedItem();
		if (message != null && message.hasAttachment()){
			saveAttachmentServices.setMessageToDownload(message);
			saveAttachmentServices.restart();
		} else
			JOptionPane.showMessageDialog(null,"Message selected is null or not content attachment");
	}

	@FXML
	void composeAction(ActionEvent event) throws IOException {
	}

	@FXML
	void addNewAccountAction(ActionEvent event) throws IOException {
		mainAnchorPane.getScene().getWindow().hide();
		stage = new Stage();
		viewFactory = ViewFactory.defaultFactory;
		stage.setScene(viewFactory.getAddNewAccount());
		stage.setTitle("Main client app");
		stage.show();
	}

    

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		downloadAttachmentLabel.setVisible(false);
		downloadAttachmentProgressBar.setVisible(false);
        downloadAttachmentBtn.setVisible(false);

		/** Get user accounts and set in model access First Step */
		emailAccountServices = new EmailAccountServices();
		ObservableList<EmailAccountFactory> currentUserAccounts = emailAccountServices.AllAccountForUser(getModelAccess().getUser().getUserID());
		for (EmailAccountFactory emailAccountFactory:currentUserAccounts){
			getModelAccess().setUserAccounts(emailAccountFactory);
		}

		/** For test model access
		 System.out.println("getModelAccess().getUserAccounts()");
		 getModelAccess().getUserAccounts().entrySet().forEach(stringEmailAccountFactoryEntry -> {
		 System.out.println(stringEmailAccountFactoryEntry.getKey()+" "+stringEmailAccountFactoryEntry.getValue());
		 });

		 System.out.println("getModelAccess().getUserAccountsAddresses()");
		 for (String s:getModelAccess().getUserAccountsAddresses()){
		 System.out.println(s);
		 }
		 */

		messageRenderServices = new MessageRenderServices(messageRender.getEngine());
		saveAttachmentServices = new SaveAttachmentServices(downloadAttachmentProgressBar,downloadAttachmentLabel);
		downloadAttachmentProgressBar.progressProperty().bind(saveAttachmentServices.progressProperty());

		
		mailTableView.setRowFactory(e-> new BoldableRowFactory<>());
		ViewFactory viewfactory = ViewFactory.defaultFactory;
		subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
		fromCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("from"));
		dateSentCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("date"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("size"));
		markStarredCol.setCellValueFactory(new PropertyValueFactory<>("markStarredButton"));
		markAttachmentCol.setCellValueFactory(new PropertyValueFactory<>("markAttachmentButton"));
		sizeCol.setComparator(new Comparator<String>() {			
			Integer int1, int2;			
			@Override
			public int compare(String o1, String o2) {
					int1 = EmailMessageBean.formattedValues.get(o1);
					int2 = EmailMessageBean.formattedValues.get(o2);
					return int1.compareTo(int2);
			}
		});
		
		
		EmailFolderBean<String> root = new EmailFolderBean<>("");
		emailFolders.setRoot(root);
		emailFolders.setShowRoot(false);

		/** Start to fetch accounts folders */
		for (EmailAccountFactory emailAccountFactory: getModelAccess().getUserAccountsFactory()){
			emailAccountFoldersServices = new EmailAccountFoldersServices (emailAccountFactory,root,getModelAccess());
			emailAccountFoldersServices.start();
		}

		folderUpdateServices = new FolderUpdateServices(getModelAccess().getFoldersList());
		folderUpdateServices.start();

		contextMenu.getItems().addAll(showDetails,deleteMessage);
		mailTableView.setContextMenu(contextMenu);
		
		emailFolders.setOnMouseClicked(e ->{
			EmailFolderBean<String> item = (EmailFolderBean<String>)emailFolders.getSelectionModel().getSelectedItem();
			if(item != null && !item.isTopElement()){
				mailTableView.setItems(item.getData());
				getModelAccess().setSelectedFolder(item);
				//clear the selected message:
				getModelAccess().setSelectedMessage(null);
			}
		});

		mailTableView.setOnMouseClicked(e->{
			EmailMessageBean message = mailTableView.getSelectionModel().getSelectedItem();
			if(message != null){
				getModelAccess().setSelectedMessage(message);
				if (message.hasAttachment()) {
					downloadAttachmentBtn.setVisible(true);
					message.setMarkAttachmentButton();
					System.out.println("setMarkAttachmentButton");
				}
				else
					downloadAttachmentBtn.setVisible(false);

				boolean value = message.isRead();
				if (!value){
					System.out.println("Message read value : "+value);
					message.setRead(true);
					seenMessageService = new SeenMessageService(getModelAccess().getSelectedFolder() , mailTableView.getSelectionModel().getSelectedItem());
					seenMessageService.restart();
					EmailFolderBean<String> selectedFolder = getModelAccess().getSelectedFolder();
					if (selectedFolder != null){
						if(value){
							selectedFolder.incrementUnreadMessagesCount(1);
						}else {
							selectedFolder.decrementUnreadMessagesCount();
						}
					}
				}
				messageRenderServices.setMessageToRender(message);
				messageRenderServices.restart();
				Button button2 = message.getMarkStarredButton();
				button2.setOnAction(event -> {
				});
			}
		});

		showDetails.setOnAction(e->{
			Stage stage = new Stage();
			viewFactory = ViewFactory.defaultFactory;
			try {
				stage.setScene(viewFactory.getEmailDetailsScene());
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
			stage.setTitle("Mail details");
			stage.show();
		});

		deleteMessage.setOnAction(e -> {
			deleteMessageService = new DeleteMessageService(getModelAccess().getSelectedFolder(), mailTableView.getSelectionModel().getSelectedItem());
			folderUpdateServices = new FolderUpdateServices(getModelAccess().getFoldersList());
			deleteMessageService.start();
			deleteMessageService.setOnSucceeded(event -> {
				JOptionPane.showMessageDialog(null,"Message deleted");
				mailTableView.getItems().remove(mailTableView.getSelectionModel().getSelectedItem());
			});
			deleteMessageService.setOnFailed(event -> {
				JOptionPane.showMessageDialog(null,"Message not deleted");
			});
			deleteMessageService.setOnCancelled(event -> {
				JOptionPane.showMessageDialog(null,"Message not deleted");
			});
		});

		markStarredCol.setStyle("-fx-alignment: CENTER;");
		markAttachmentCol.setStyle("-fx-alignment: CENTER;");
	}
}
