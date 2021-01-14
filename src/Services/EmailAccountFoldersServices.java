package Services;

import Model.EmailAccountBean;
import Model.EmailAccountConstants;
import Model.EmailAccountFactory;
import Model.EmailFolderBean;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class EmailAccountFoldersServices extends Service<Integer> {

    private String emailAddress;
    private String emailPassword;
    private String emailType;
    private EmailFolderBean<String> folders;

    private EmailAccountBean emailAccountBean;
    private EmailAccountFactory emailAccountFactory;

    public EmailAccountFoldersServices(EmailAccountFactory emailAccountFactory,EmailFolderBean<String> folders) {
        this.emailAccountBean = emailAccountFactory.createEmailAccount();
        this.emailAddress = emailAccountBean.getEmailAddress();
        this.emailPassword = emailAccountBean.getEmailPassword();
        this.emailType = emailAccountBean.getType();
        this.folders = folders;
    }

    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                emailAccountFactory = new EmailAccountFactory(emailAddress,emailPassword,emailType);
                emailAccountBean = emailAccountFactory.createEmailAccount();
                if (emailAccountBean.getLoginState() == EmailAccountConstants.LOGIN_STATE_SUCCEEDED){
                    EmailFolderBean<String> emailFolderBean = new EmailFolderBean<>(emailAddress);
                    folders.getChildren().add(emailFolderBean);
                    FetchEmailAccountFoldersServices fetchEmailAccountFoldersServices = new FetchEmailAccountFoldersServices(emailFolderBean,emailAccountFactory);
                    fetchEmailAccountFoldersServices.start();
                }
                return emailAccountBean.getLoginState();
            }
        };
    }
}
