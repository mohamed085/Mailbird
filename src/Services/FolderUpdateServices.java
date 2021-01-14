package Services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import java.util.List;

public class FolderUpdateServices extends Service<Void> {

    private List<Folder> foldersList;

    public FolderUpdateServices(List<Folder> foldersList) {
        this.foldersList = foldersList;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (;;){
                    try {
                        Thread.sleep(10000);
                        System.out.println("Checking for new mails");
                        if (FetchEmailAccountFoldersServices.noServicesAction()){
                            System.out.println("Checked Folder");
                            for (Folder folder:foldersList){
                                if (folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen()){
                                    folder.getMessageCount();
                                }
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
