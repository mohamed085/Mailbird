package Services;

import Model.EmailAccountBean;
import Model.EmailAccountFactory;
import Model.EmailFolderBean;
import Model.ModelAccess;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;

public class FetchEmailAccountFoldersServices extends Service<Void> {

    private EmailFolderBean<String> foldersRoot;
    private EmailAccountBean emailAccount;
    private ModelAccess modelAccess;
    private static int NUMBER_OF_FETCHFOLDERSSERVICES_ACTIVE = 0;

    public FetchEmailAccountFoldersServices(EmailFolderBean<String> foldersRoot, EmailAccountFactory emailAccountFactory) {
        this.foldersRoot = foldersRoot;
        this.emailAccount = emailAccountFactory.createEmailAccount();
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (emailAccount != null ) {
                    Folder[] folders = emailAccount.getStore().getDefaultFolder().list();
                    for (Folder folder:folders){
                        EmailFolderBean<String> emailFoldersItem = new EmailFolderBean<String>(folder.getName(), folder.getFullName());
                        foldersRoot.getChildren().add(emailFoldersItem);
                        emailFoldersItem.setExpanded(true);
                        System.out.println("Added : "+folder.getFullName());
                        Folder[] subFolders = folder.list();
                        for (Folder subfolder:subFolders) {
                            EmailFolderBean<String> emailFoldersSubItem = new EmailFolderBean<String>(subfolder.getName(), subfolder.getFullName());
                            emailFoldersItem.getChildren().add(emailFoldersSubItem);
                            emailFoldersSubItem.setExpanded(true);
                            System.out.println("Added : "+subfolder.getFullName());
                        }
                    }
                }
                return null;
            }
        };
    }
}
