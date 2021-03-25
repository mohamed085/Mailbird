package DAO;

import Model.EmailAccountBean;
import Model.EmailAccountFactory;
import Model.UserBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmailAccountDAO {
    EmailAccountFactory emailAccountFactory;
    EmailAccountBean emailAccountBean;
    UserBean userBean;

    public EmailAccountFactory addNewAccount(int userID, EmailAccountFactory emailAccountFactory){
        emailAccountBean = emailAccountFactory.createEmailAccount();
        PreparedStatement pre;
        Connection connection = DBConnection.getConnection();
        try {
            pre = connection.prepareStatement("INSERT INTO emailAccount (emailAddress, emailPassword, type, userID) values (?,?,?,?);");
            pre.setString(1,emailAccountBean.getEmailAddress());
            pre.setString(2,emailAccountBean.getEmailPassword());
            pre.setString(3,emailAccountBean.getType());
            pre.setInt(4,userID);
            pre.executeUpdate();
            emailAccountFactory = new EmailAccountFactory(emailAccountBean.getEmailAddress(),emailAccountBean.getEmailPassword(),emailAccountBean.getType());
            return emailAccountFactory;
        }catch (Exception ex){
            System.out.println("Wrong EmailAccountDAO.addNewAccount");
            ex.printStackTrace();
            return null;
        }
    }

    //returnAllAccountForUser
    public ObservableList<EmailAccountFactory> AllAccountForUser(int userID){
        ObservableList<EmailAccountFactory> currentUserAccounts = FXCollections.observableArrayList();
        PreparedStatement pre;
        ResultSet resultSet = null;
        Connection connection = DBConnection.getConnection();
        try {
            pre = connection.prepareStatement("SELECT * from emailAccount WHERE userID=? ");
            pre.setInt(1,userID);
            resultSet = pre.executeQuery();
            while (resultSet.next()){
                emailAccountFactory = new EmailAccountFactory(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
                currentUserAccounts.add(emailAccountFactory);
            }
        }catch (Exception ex){
            System.out.println("Wrong EmailAccountDAO.AllAccountForUser");
            ex.printStackTrace();
            return null;
        }
        return currentUserAccounts;
    }
}
