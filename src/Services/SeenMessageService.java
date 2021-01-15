package Services;

import Model.EmailFolderBean;
import Model.EmailMessageBean;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.swing.*;

public class SeenMessageService extends Service {

    private EmailFolderBean<String> folderBean;
    private EmailMessageBean messageBean;
    private Folder folder;

    public SeenMessageService(EmailFolderBean folderBean , EmailMessageBean messageBean) {
        this.messageBean = messageBean;
        this.folderBean = folderBean;
    }


    @Override
    protected Task createTask() {
         return new Task() {
            @Override
            protected Object call() throws Exception
            {
                messageBean.getMessageReference().setFlags(new Flags(Flags.Flag.SEEN), true);
                folderBean.decrementUnreadMessagesCount();
                return null;
            }
        };
    }
}
