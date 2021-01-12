package Services;

import DAO.UserDAO;
import Model.UserBean;

public class UserServices {
    UserDAO userDAO;
    UserBean currentUSer;

    public boolean addNewUSer(UserBean userBean){
        userDAO = new UserDAO();
        currentUSer = userDAO.addNewUser(userBean);
        if (currentUSer != null){
            System.out.println(currentUSer.toString());
            return true;
        }
        else{
            System.out.println("Something wrong UserServices.addNewUSer");
            return false;
        }
    }

    public UserBean searchForUser(UserBean userBean){
        userDAO = new UserDAO();
        currentUSer = userDAO.searchForUser(userBean);
        if (currentUSer != null){
            System.out.println(currentUSer.toString());
            return currentUSer;
        }
        else {
            System.out.println("Something wrong UserServices.searchForUser");
            return null;
        }
    }


}
