package Model;

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
        Properties properties = new Properties();
        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");
        Session emailSession = Session.getDefaultInstance(properties);

        //create the POP3 store object and connect with the pop server
        try {
            Store store = emailSession.getStore("pop3s");
            store.connect(host, GmailAddress, GmailPassword);
            System.out.println("EmailAccountBean constructed succesufully!!!");
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
    public int getLoginState() {
        return loginState;
    }
}
