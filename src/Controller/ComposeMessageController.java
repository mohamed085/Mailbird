package Controller;

import Model.EmailAccountConstants;
import Model.ModelAccess;

import Services.SendMailServices;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;

import javax.swing.*;


public class ComposeMessageController extends AbstractController implements Initializable {

    public ComposeMessageController(ModelAccess modelAccess) {
        super(modelAccess);
    }

    private List<File> attachments = new ArrayList<File>();
    SendMailServices sendMailServices;

    @FXML
    private AnchorPane composeAnchorPane;

    @FXML
    private ComboBox<String> fromComboBox;

    @FXML
    private TextField subjectTextFiled;

    @FXML
    private Label attachmentName;

    @FXML
    private TextField toTextFiled;


    @FXML
    private HTMLEditor content;

    @FXML
    void AddAttachmentAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            attachments.add(selectedFile);
            attachmentName.setText(selectedFile.getName() + "; ");
        }

        else
            JOptionPane.showMessageDialog(null,"Please chose file");
    }

    @FXML
    void sendMailAction(ActionEvent event) {
         sendMailServices = new SendMailServices(getModelAccess().getEmailAccountByAddress(fromComboBox.getValue()), subjectTextFiled.getText(),toTextFiled.getText(),content.getHtmlText(),attachments);
         sendMailServices.restart();
         sendMailServices.setOnSucceeded(event1 -> {
             if (sendMailServices.getValue() == EmailAccountConstants.MESSAGE_SENT_OK){
                 JOptionPane.showMessageDialog(null, "Message sent successfully");
                 composeAnchorPane.getScene().getWindow().hide();
            }
             else
                 JOptionPane.showMessageDialog(null,"Message not sent");
         });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fromComboBox.setItems(getModelAccess().getUserAccountsAddresses());
        fromComboBox.setValue(getModelAccess().getUserAccountsAddresses().get(0));
    }
}
