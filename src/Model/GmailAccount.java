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
        Session emailSession = Session.getDefaultInstance(properties);

        //create the IMAP store object and connect with the pop server
        try {
            store = emailSession.getStore("imaps");
            store.connect(host, GmailAddress, GmailPassword);
            System.out.println("EmailAccountBean constructed successfully!!!");
            loginState = EmailAccountConstants.LOGIN_STATE_SUCCEEDED;
        } catch (Exception e) {
            e.printStackTrace();
            loginState = EmailAccountConstants.LOGIN_STATE_FAILED_BY_CREDENTIALS;
        }
    }


    @Override
    public void addEmailsToData(ObservableList<EmailMessageBean> data){
        try {
            System.out.println("Thread that is fetching emails: " + Thread.currentThread().getName());
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            for(int i = folder.getMessageCount(); i > 0; i--){
                Message message = folder.getMessage(i);
                EmailMessageBean messageBean = new EmailMessageBean(message.getSubject(),
                        message.getFrom()[0].toString(),
                        message.getSize(),
                        "",
                        message.getFlags().contains(Flags.Flag.SEEN));
                System.out.println("Got: " + messageBean);
                data.add(messageBean);

            }

        } catch (Exception e) {
            e.printStackTrace();
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
