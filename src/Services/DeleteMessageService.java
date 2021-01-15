package Services;


import Model.EmailFolderBean;
import Model.EmailMessageBean;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.swing.*;

public class DeleteMessageService extends Service {
    private EmailFolderBean<String> folderBean;
    private EmailMessageBean messageBean;
    private Folder folder;

    public DeleteMessageService(EmailFolderBean folderBean , EmailMessageBean messageBean) {
        this.messageBean = messageBean;
        this.folderBean = folderBean;
        setOnSucceeded(event -> {
            JOptionPane.showMessageDialog(null,"Message deleted");
        });
        setOnFailed(event -> {
            JOptionPane.showMessageDialog(null,"Message not deleted");
        });
        setOnCancelled(event -> {
            JOptionPane.showMessageDialog(null,"Message not deleted");
        });
    }

    @Override
    protected Task createTask()
    {
        return new Task() {
            @Override
            protected Object call() throws Exception
            {
                messageBean.getMessageReference().setFlag(Flags.Flag.DELETED, true);
                folderBean.decrementUnreadMessagesCount();
                return null;
            }
        };
    }
}
