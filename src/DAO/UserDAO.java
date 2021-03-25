package DAO;

import Model.UserBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    UserBean userBean;

    public UserBean addNewUser(UserBean user){
        PreparedStatement pre;
        Connection connection = DBConnection.getConnection();
        try {
            pre = connection.prepareStatement("insert into user (username, password) values (?,?);");
            pre.setString(1,user.getUsername());
            pre.setString(2,user.getPassword());
            pre.executeUpdate();
            return user;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    //returnCurrentUserWithID
    public UserBean searchForUser(UserBean user){
        PreparedStatement pre;
        ResultSet resultSet = null;
        Connection connection = DBConnection.getConnection();
        try {
            pre = connection.prepareStatement("SELECT * from user WHERE username=? AND password=?");
            pre.setString(1,user.getUsername());
            pre.setString(2,user.getPassword());
            resultSet = pre.executeQuery();
            if(resultSet.next()){
                userBean = new UserBean(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3));
                return userBean;
            }else
                return null;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

}
