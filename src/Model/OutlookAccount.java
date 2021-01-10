package Model;

import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

public class OutlookAccount implements EmailAccountBean {
    private String OutlookAddress;
    private String OutlookPassword;
    private final String type = "Outlook";

    private Properties properties;
    private Store store;
    private Session session;
    private int loginState = EmailAccountConstants.LOGIN_STATE_NOT_READY;

    public OutlookAccount(String outlookAddress, String outlookPassword) {
        OutlookAddress = outlookAddress;
        OutlookPassword = outlookPassword;

        System.out.println("OutlookAccount");

        String host = "outlook.office365.com";// change accordingly
        Properties properties = new Properties();
        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");
        Session emailSession = Session.getDefaultInstance(properties);

        //create the POP3 store object and connect with the pop server
        try {
            Store store = emailSession.getStore("pop3s");
            store.connect(host, OutlookAddress, OutlookPassword);
            System.out.println("EmailAccountBean constructed succesufully!!!");
            loginState = EmailAccountConstants.LOGIN_STATE_SUCCEEDED;
        } catch (Exception e) {
            e.printStackTrace();
            loginState = EmailAccountConstants.LOGIN_STATE_FAILED_BY_CREDENTIALS;
        }
    }

    @Override
    public String getEmailAddress() {
        return OutlookAddress;
    }

    @Override
    public String getEmailPassword() {
        return OutlookPassword;
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
