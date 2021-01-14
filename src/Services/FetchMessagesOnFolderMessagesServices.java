package Services;

import Model.EmailFolderBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.mail.Folder;
import javax.mail.Message;

public class FetchMessagesOnFolderMessagesServices extends Service<Void>{
    private EmailFolderBean<String> emailFolder;
    private Folder folder;


    public FetchMessagesOnFolderMessagesServices(EmailFolderBean<String> emailFolder, Folder folder) {
        this.emailFolder = emailFolder;
        this.folder = folder;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (folder.getType() != Folder.HOLDS_FOLDERS)
                    folder.open(Folder.READ_ONLY);
                for(int i = folder.getMessageCount(); (folder.getMessageCount() > 100) ? i > folder.getMessageCount() - 100 : i > 0 ; i--){
                    Message currentMessage = folder.getMessage(i);
                    emailFolder.addEmail(-1,currentMessage);
                }
                return null;
            }
        };
    }
}
