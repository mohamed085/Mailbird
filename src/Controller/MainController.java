package Controller;

import Model.EmailAccountBean;
import Model.EmailAccountFactory;
import Model.ModelAccess;
import Services.EmailAccountServices;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends AbstractController implements Initializable {

    EmailAccountServices emailAccountServices;
    EmailAccountBean emailAccountBean;


    @FXML
    private TreeView<?> emailFolders;

    @FXML
    private TableColumn<?, ?> sizeCol;

    @FXML
    private ProgressBar downloadAttachmentProgressBar;

    @FXML
    private TableView<?> mailTableView;

    @FXML
    private Label downloadAttachmentLabel;

    @FXML
    private TableColumn<?, ?> fromCol;

    @FXML
    private Button readMessage;

    @FXML
    private Button downloadAttachmentBtn;

    @FXML
    private TableColumn<?, ?> subjectCol;

    @FXML
    private WebView messageRender;

    @FXML
    private TableColumn<?, ?> dateSentCol;

    @FXML
    void readMessageAction(ActionEvent event) {

    }

    @FXML
    void downloadAttachmentBtnAction(ActionEvent event) {

    }

    @FXML
    void composeAction(ActionEvent event) {

    }

    @FXML
    void addNewAccountAction(ActionEvent event) {

    }

    public MainController(ModelAccess modelAccess) {
        super(modelAccess);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("ss");
        emailAccountServices = new EmailAccountServices();
        ObservableList<EmailAccountFactory> currentUserAccounts = emailAccountServices.AllAccountForUser(getModelAccess().getUser().getUserID());
        for (EmailAccountFactory emailAccountFactory:currentUserAccounts){
            System.out.println(emailAccountFactory.toString());
        }


    }
}
