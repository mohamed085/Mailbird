package Services;

import Model.EmailAccountBean;
import Model.EmailAccountFactory;
import Model.EmailFolderBean;
import Model.ModelAccess;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;

public class FetchEmailAccountFoldersServices extends Service<Void> {

    private EmailFolderBean<String> foldersRoot;
    private EmailAccountBean emailAccount;
    private ModelAccess modelAccess;

    Folder[] folders;
    Folder[] subFolders;
    EmailFolderBean<String> emailFoldersItem;
    EmailFolderBean<String> emailFoldersSubItem;

    private static int NUMBER_OF_FETCHFOLDERSSERVICES_ACTIVE = 0;
    private FetchMessagesOnFolderMessagesServices fetchMessagesOnFolderMessagesServices;

    public FetchEmailAccountFoldersServices(EmailFolderBean<String> foldersRoot, EmailAccountFactory emailAccountFactory, ModelAccess modelAccess) {
        this.foldersRoot = foldersRoot;
        this.emailAccount = emailAccountFactory.createEmailAccount();
        this.modelAccess = modelAccess;
        this.setOnSucceeded(event -> {
            NUMBER_OF_FETCHFOLDERSSERVICES_ACTIVE--;
        });
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (emailAccount != null ) {
                    folders = emailAccount.getStore().getDefaultFolder().list();
                    for (Folder folder:folders){
                        modelAccess.addFolder(folder);
                        emailFoldersItem = new EmailFolderBean<String>(folder.getName(), folder.getFullName());
                        foldersRoot.getChildren().add(emailFoldersItem);
                        emailFoldersItem.setExpanded(true);
                        addMessageListenerToFolder(folder,emailFoldersItem);
                        fetchMessagesOnFolderMessagesServices = new FetchMessagesOnFolderMessagesServices(emailFoldersItem,folder);
                        fetchMessagesOnFolderMessagesServices.start();
                        System.out.println("Added : "+folder.getFullName());
                        subFolders = folder.list();
                        for (Folder subfolder:subFolders) {
                            modelAccess.addFolder(subfolder);
                            emailFoldersSubItem = new EmailFolderBean<String>(subfolder.getName(), subfolder.getFullName());
                            emailFoldersItem.getChildren().add(emailFoldersSubItem);
                            emailFoldersSubItem.setExpanded(true);
                            addMessageListenerToFolder(subfolder,emailFoldersSubItem);
                            fetchMessagesOnFolderMessagesServices = new FetchMessagesOnFolderMessagesServices(emailFoldersSubItem,subfolder);
                            fetchMessagesOnFolderMessagesServices.start();
                            System.out.println("Added : "+subfolder.getFullName());
                        }
                    }
                }
                return null;
            }
        };
    }

    private void addMessageListenerToFolder(Folder folder,EmailFolderBean<String> item){
        folder.addMessageCountListener(new MessageCountListener() {
            @Override
            public void messagesAdded(MessageCountEvent messageCountEvent) {
                for (int i = 0 ; i < messageCountEvent.getMessages().length ; i++){
                    Message currentMessage = null;
                    try {
                        currentMessage = folder.getMessage(folder.getMessageCount() -i );
                        item.addEmail(0,currentMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void messagesRemoved(MessageCountEvent messageCountEvent) {

            }
        });
    }

    public static boolean noServicesAction(){
        return NUMBER_OF_FETCHFOLDERSSERVICES_ACTIVE == 0;
    }
}
