package Controller;

import Model.EmailAccountBean;
import Model.EmailAccountFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AddNewAccountController implements Initializable {

    @FXML
    private PasswordField passwordTextFiled;

    @FXML
    private AnchorPane addNewAccountAnchorPane;

    @FXML
    private ComboBox<String> AccountType;

    @FXML
    private TextField usernameTextFiled;

    @FXML
    void AddNewAccount(ActionEvent event) {
        EmailAccountFactory newEmail = new EmailAccountFactory(usernameTextFiled.getText(), passwordTextFiled.getText(), AccountType.getValue());
        EmailAccountBean emailAccountBean = newEmail.createEmailAccount();
        if (emailAccountBean != null){
            addNewAccountAnchorPane.getScene().getWindow().hide();
            /**
             * Go To Main Layout
             */
       }
        else
            JOptionPane.showMessageDialog(null,"Invalid email account");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AccountType.getItems().addAll("Gmail","Outlook");
    }
}
