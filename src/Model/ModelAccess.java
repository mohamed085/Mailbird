package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class ModelAccess {
    private UserBean user = new UserBean();

    private EmailAccountBean emailAccountBean;
    private Map<String, EmailAccountFactory> userAccounts = new HashMap<String, EmailAccountFactory>();
    private ObservableList<String> userAccountsAddresses = FXCollections.observableArrayList();

    private EmailMessageBean selectedMessage;
    private EmailFolderBean<String> selectedFolder;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public Map<String, EmailAccountFactory> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(EmailAccountFactory userAccount) {
        emailAccountBean = userAccount.createEmailAccount();
        userAccounts.put(emailAccountBean.getEmailAddress(),userAccount);
        userAccountsAddresses.add(emailAccountBean.getEmailAddress());
    }

    public ObservableList<String> getUserAccountsAddresses() {
        return userAccountsAddresses;
    }

    public EmailMessageBean getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessageBean selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public EmailFolderBean<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailFolderBean<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }


}
