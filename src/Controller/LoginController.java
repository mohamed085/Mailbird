package Controller;

import Model.ModelAccess;
import Model.UserBean;
import Services.UserServices;
import View.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class LoginController extends AbstractController{

    ViewFactory viewFactory;
    Stage stage;
    UserBean currentUser;

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private PasswordField passwordTextFiled;

    @FXML
    private TextField usernameTextFiled;


    @FXML
    void loginAction(ActionEvent event) throws IOException {
        UserBean userBean = new UserBean(usernameTextFiled.getText(),passwordTextFiled.getText());
        UserServices userServices = new UserServices();
        currentUser = userServices.searchForUser(userBean);
        if (currentUser != null){
            getModelAccess().setUser(currentUser);
            loginAnchorPane.getScene().getWindow().hide();
            stage = new Stage();
            viewFactory = ViewFactory.defaultViewFactory;
            stage.setScene(viewFactory.getMainScene());
            stage.setTitle("Main client app");
            stage.show();
        }else
            JOptionPane.showMessageDialog(null ,"Invalid input");
    }

    @FXML
    void SignUpAction(ActionEvent event) throws IOException {
        loginAnchorPane.getScene().getWindow().hide();
        stage = new Stage();
        viewFactory = ViewFactory.defaultViewFactory;
        stage.setScene(viewFactory.getRegisterScene());
        stage.setTitle("Register");
        stage.show();
    }

    public LoginController(ModelAccess modelAccess) {
        super(modelAccess);
    }

}