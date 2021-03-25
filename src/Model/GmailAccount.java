package Model;

import javafx.collections.ObservableList;

import javax.mail.*;
import java.util.Properties;

public class GmailAccount implements EmailAccountBean {
    private String GmailAddress;
    private String GmailPassword;
    private final String type = "Gmail";

    private Properties properties;
    private Store store;
    private Session session;
    private int loginState = EmailAccountConstants.LOGIN_STATE_NOT_READY;

    public GmailAccount(String gmailAddress, String gmailPassword) {
        GmailAddress = gmailAddress;
        GmailPassword = gmailPassword;

        System.out.println("GmailAccount");

        String host = "pop.gmail.com";// change accordingly

        properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtps.host", "smtp.gmail.com");
        properties.put("mail.smtps.auth", "true");
        properties.put("incomingHost", "imap.gmail.com");
        properties.put("outgoingHost", "smtp.gmail.com");


        //Connecting:
        Session session = Session.getDefaultInstance(properties, null);
        try {
            this.store = session.getStore();
            store = session.getStore("imaps");
            store.connect(properties.getProperty("incomingHost"),GmailAddress, GmailPassword);
            System.out.println("EmailAccountBean constructed successfully!!!");
            loginState = EmailAccountConstants.LOGIN_STATE_SUCCEEDED;
        } catch (Exception e) {
            e.printStackTrace();
            loginState = EmailAccountConstants.LOGIN_STATE_FAILED_BY_CREDENTIALS;
        }
    }

    @Override
    public String getEmailAddress() {
        return GmailAddress;
    }

    @Override
    public String getEmailPassword() {
        return GmailPassword;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Store getStore() {
        return store;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public int getLoginState() {
        return loginState;
    }
}
