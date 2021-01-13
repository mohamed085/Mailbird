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

public class RegisterController extends AbstractController{

    ViewFactory viewFactory;
    UserBean currentUser;

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
        if (userServices.addNewUSer(userBean)){
            currentUser = userServices.searchForUser(userBean);
            getModelAccess().setUser(currentUser);
            registerAnchorPane.getScene().getWindow().hide();
            Stage stage = new Stage();
            stage.setScene(viewFactory.getAddNewAccount());
            stage.setTitle("Add new account");
            stage.show();
        }else
            JOptionPane.showMessageDialog(null ,"Invalid input");
    }

    @FXML
    void loginAction(ActionEvent event) throws IOException {
        registerAnchorPane.getScene().getWindow().hide();
        viewFactory = ViewFactory.defaultFactory;
        Stage stage = new Stage();
        stage.setScene(viewFactory.getLoginScene());
        stage.setTitle("Login");
        stage.show();
    }

    public RegisterController(ModelAccess modelAccess) {
        super(modelAccess);
    }

}