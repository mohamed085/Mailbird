package Controller;

import Model.EmailAccountBean;
import Model.EmailAccountFactory;
import Model.ModelAccess;
import Services.EmailAccountServices;
import View.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddNewAccountController extends AbstractController implements Initializable {

    ViewFactory viewFactory;
    EmailAccountServices emailAccountServices;

    @FXML
    private AnchorPane addNewAccountAnchorPane;

    @FXML
    private PasswordField passwordTextFiled;

    @FXML
    private TextField usernameTextFiled;

    @FXML
    private ComboBox<String> AccountType;

    @FXML
    void AddNewAccount(ActionEvent event) throws IOException {
        EmailAccountFactory newEmail = new EmailAccountFactory(usernameTextFiled.getText(), passwordTextFiled.getText(), AccountType.getValue());
        EmailAccountBean emailAccountBean = newEmail.createEmailAccount();
        if (emailAccountBean != null){
            emailAccountServices = new EmailAccountServices();
            emailAccountServices.addNewAccount(getModelAccess().getUser().getUserID(),newEmail);
            addNewAccountAnchorPane.getScene().getWindow().hide();
            viewFactory = ViewFactory.defaultFactory;
            Stage stage = new Stage();
            stage.setScene(viewFactory.getMainScene());
            stage.setTitle("Mail client application");
            stage.show();
        }
        else
            JOptionPane.showMessageDialog(null,"Invalid email account");
    }

    public AddNewAccountController(ModelAccess modelAccess) {
        super(modelAccess);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AccountType.getItems().addAll("Gmail","Outlook");
    }
}