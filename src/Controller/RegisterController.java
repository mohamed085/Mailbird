package Controller;

import Model.UserBean;
import Services.UserServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML
    private AnchorPane registerAnchorPane;

    @FXML
    private TextField newUsernameTextFiled;

    @FXML
    private PasswordField newPasswordTextFiled;

    @FXML
    void RegisterAction(ActionEvent event) throws IOException {
        UserBean userBean = new UserBean(newUsernameTextFiled.getText(),newPasswordTextFiled.getText());
        System.out.println(userBean.toString());
        UserServices userServices =new UserServices();
        userServices.addNewUSer(userBean);
        registerAnchorPane.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../View/AddNewAccount.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Login in mail client app");
        stage.show();
    }

    @FXML
    void loginAction(ActionEvent event) throws IOException {
        registerAnchorPane.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Login in mail client app");
        stage.show();
    }

}
