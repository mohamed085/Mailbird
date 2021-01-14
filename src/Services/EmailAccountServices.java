package Services;

import DAO.EmailAccountDAO;
import Model.EmailAccountBean;
import Model.EmailAccountFactory;
import javafx.collections.ObservableList;

public class EmailAccountServices {
    EmailAccountBean emailAccountBean;
    EmailAccountFactory emailAccountFactory;
    EmailAccountDAO emailAccountDAO;

    public void addNewAccount(int userID, EmailAccountFactory emailAccountFactory){
        emailAccountDAO = new EmailAccountDAO();
        emailAccountFactory = emailAccountDAO.addNewAccount(userID, emailAccountFactory);
        if (emailAccountFactory != null){
            emailAccountBean = emailAccountFactory.createEmailAccount();
            System.out.println(emailAccountBean.toString());
        }
        else
            System.out.println("Something wrong EmailAccountServices.addNewAccount");

    }

    public ObservableList<EmailAccountFactory> AllAccountForUser(int userID){
        emailAccountDAO = new EmailAccountDAO();
        ObservableList<EmailAccountFactory> currentUserAccounts = emailAccountDAO.AllAccountForUser(userID);
        return currentUserAccounts;
    }



}
