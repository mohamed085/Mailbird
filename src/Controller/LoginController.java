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

public class LoginController {

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private PasswordField passwordTextFiled;

    @FXML
    private TextField usernameTextFiled;

    @FXML
    void loginAction(ActionEvent event) {
        UserBean userBean = new UserBean(usernameTextFiled.getText(),passwordTextFiled.getText());
        System.out.println(userBean.toString());
        UserServices userServices =new UserServices();
        userServices.addNewUSer(userBean);
        loginAnchorPane.getScene().getWindow().hide();
    }

    @FXML
    void SignUpAction(ActionEvent event) throws IOException {
        loginAnchorPane.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../View/Register.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Register in mail client app");
        stage.show();
    }

}
