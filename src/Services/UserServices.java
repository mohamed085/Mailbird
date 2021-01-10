package Services;

import DAO.UserDAO;
import Model.UserBean;

public class UserServices {
    UserDAO userDAO;
    UserBean currentUSer;

    public void addNewUSer(UserBean userBean){
        userDAO = new UserDAO();
        currentUSer = userDAO.addNewUser(userBean);
        if (currentUSer != null){
            System.out.println(currentUSer.toString());
        }
        else
            System.out.println("Something wrong UserServices.addNewUSer");
    }

    public void searchForUser(UserBean userBean){
        userDAO = new UserDAO();
        currentUSer = userDAO.searchForUser(userBean);
        if (currentUSer != null){
            System.out.println(currentUSer.toString());
        }
        else
            System.out.println("Something wrong UserServices.searchForUser");
    }
}
