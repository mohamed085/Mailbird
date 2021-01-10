package Services;

import DAO.EmailAccountDAO;
import Model.EmailAccountBean;
import Model.EmailAccountFactory;

import java.util.List;

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

    public void AllAccountForUser(int userID){
        emailAccountDAO = new EmailAccountDAO();
        List<EmailAccountFactory> currentUserAccounts = emailAccountDAO.AllAccountForUser(2);
        if (currentUserAccounts.size() > 0){
            for (EmailAccountFactory emailAccountFactory:currentUserAccounts){
                emailAccountBean = emailAccountFactory.createEmailAccount();
                System.out.println(emailAccountBean.getEmailAddress()+" "+emailAccountBean.getEmailPassword()+" "+emailAccountBean.getType());
            }
        }
        else
            System.out.println("Something wrong EmailAccountServices.AllAccountForUser");
    }
}
