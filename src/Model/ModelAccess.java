package Model;

import javafx.collections.ObservableList;

public class ModelAccess {
    private UserBean user = new UserBean();
    private ObservableList<EmailAccountFactory> userAccounts;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public ObservableList<EmailAccountFactory> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(ObservableList<EmailAccountFactory> userAccounts) {
        this.userAccounts = userAccounts;
    }

}
