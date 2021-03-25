package Model;

import javafx.collections.ObservableList;

import javax.mail.*;
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

        properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        //extra codes required for reading OUTLOOK mails during IMAP-start
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.host", "outlook.office365.com");
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", "587");
        properties.put("incomingHost", "outlook.office365.com");
        properties.put("outgoingHost", "smtp.office365.com");

        Authenticator auth = new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(OutlookAddress, OutlookPassword);
            }
        };

        session = Session.getInstance(properties, auth);
        try {
            this.store = session.getStore();
            store.connect(properties.getProperty("incomingHost"), OutlookAddress, OutlookPassword);
            System.out.println("Outlook account constructed successfully!!!");
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
    public Properties getProperties() {
        return properties;
    }

    @Override
    public int getLoginState() {
        return loginState;
    }

}
