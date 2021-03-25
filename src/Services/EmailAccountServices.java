package Services;

import Model.EmailAccountFactory;
import javafx.collections.ObservableList;

public interface EmailAccountServices {
    public void addNewAccount(int userID, EmailAccountFactory emailAccountFactory);
    public ObservableList<EmailAccountFactory> AllAccountForUser(int userID);
}
