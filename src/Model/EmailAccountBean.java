package Model;

import javafx.collections.ObservableList;

import javax.mail.Session;
import javax.mail.Store;

public interface EmailAccountBean {
    public String getEmailAddress();
    public String getEmailPassword();
    public String getType();
    public Store getStore();
    public Session getSession();
    public int getLoginState();
}
