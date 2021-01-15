package Controller;

import Model.*;
import Services.EmailAccountFoldersServices;
import Services.EmailAccountServices;
import Services.FolderUpdateServices;
import Services.MessageRenderServices;
import View.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

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
	private EmailAccountFactory emailAccountFactory;
	private EmailAccountServices emailAccountServices;
	private FolderUpdateServices folderUpdateServices;
	private MessageRenderServices messageRenderServices;
	private EmailAccountFoldersServices emailAccountFoldersServices;



	@FXML
	private AnchorPane mainAnchorPane;

	@FXML
	private TreeView<String> emailFolders;
	private MenuItem showDetails = new MenuItem("show details");
	private TreeItem<String> root  = new TreeItem<String>();

	@FXML
	private TableView<EmailMessageBean> mailTableView;

	@FXML
	private TableColumn<EmailMessageBean, String> fromCol,subjectCol,dateSentCol,sizeCol;

	@FXML
	private WebView messageRender;

	@FXML
	private ProgressBar downloadAttachmentProgressBar;

	@FXML
	private Label downloadAttachmentLabel;

	@FXML
	private Button readMessage,downloadAttachmentBtn;


	@FXML
	void readMessageAction(ActionEvent event) {
	}

	@FXML
	void downloadAttachmentBtnAction(ActionEvent event) {
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
		downloadAttachmentProgressBar.setVisible(false);
		downloadAttachmentLabel.setVisible(false);

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

		mailTableView.setRowFactory(e-> new BoldableRowFactory<>());
		ViewFactory viewfactory = ViewFactory.defaultFactory;
		subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("subject"));
		fromCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("from"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessageBean, String>("size"));		
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

		mailTableView.setContextMenu(new ContextMenu(showDetails));
		
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
				messageRenderServices.setMessageToRender(message);
				messageRenderServices.restart();
			}
		});
		showDetails.setOnAction(e->{
			
			Scene scene = viewfactory.getEmailDetailsScene();
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		});		
		
		
	}
	

}
