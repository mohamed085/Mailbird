package Services;

import Model.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class EmailAccountFoldersServices extends Service<Integer> {

    private String emailAddress;
    private String emailPassword;
    private String emailType;
    private EmailFolderBean<String> folders;
    private ModelAccess modelAccess;

    private EmailAccountBean emailAccountBean;
    private EmailAccountFactory emailAccountFactory;

    public EmailAccountFoldersServices(EmailAccountFactory emailAccountFactory,EmailFolderBean<String> folders, ModelAccess modelAccess) {
        this.emailAccountBean = emailAccountFactory.createEmailAccount();
        this.emailAddress = emailAccountBean.getEmailAddress();
        this.emailPassword = emailAccountBean.getEmailPassword();
        this.emailType = emailAccountBean.getType();
        this.folders = folders;
        this.modelAccess = modelAccess;
    }

    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                emailAccountFactory = new EmailAccountFactory(emailAddress,emailPassword,emailType);
                emailAccountBean = emailAccountFactory.createEmailAccount();
                System.out.println("1: "+EmailAccountConstants.LOGIN_STATE_SUCCEEDED);
                System.out.println("2: "+emailAccountBean.getLoginState());
                if (emailAccountBean.getLoginState() == EmailAccountConstants.LOGIN_STATE_SUCCEEDED){
                    System.out.println("ads");
                    EmailFolderBean<String> emailFolderBean = new EmailFolderBean<>(emailAddress);
                    folders.getChildren().add(emailFolderBean);
                    FetchEmailAccountFoldersServices fetchEmailAccountFoldersServices = new FetchEmailAccountFoldersServices(emailFolderBean,emailAccountFactory,modelAccess);
                        fetchEmailAccountFoldersServices.start();
                }
                return emailAccountBean.getLoginState();
            }
        };
    }
}
