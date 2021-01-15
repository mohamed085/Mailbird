package Services;

import Model.EmailMessageBean;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import javax.mail.internet.MimeBodyPart;

public class SaveAttachmentServices extends Service<Void> {

    private String LOCATION_OF_DOWNLOADS = System.getProperty("user.home")+"/Downloads/";
    private EmailMessageBean messageToDownload;
    private ProgressBar downloadProgress;
    private Label downloadLabel;

    public SaveAttachmentServices(ProgressBar downloadProgress, Label downloadLabel) {
        this.downloadProgress = downloadProgress;
        this.downloadLabel = downloadLabel;
        this.setOnRunning(event -> {
            showElement(true);
        });
        this.setOnSucceeded(event -> {
            showElement(false);
        });
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    for (MimeBodyPart mimeBodyPart:messageToDownload.getAttachmentsList()) {
                        updateProgress(messageToDownload.getAttachmentsList().indexOf(mimeBodyPart), messageToDownload.getAttachmentsList().size());
                        mimeBodyPart.saveFile(LOCATION_OF_DOWNLOADS + mimeBodyPart.getFileName());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    public void setMessageToDownload(EmailMessageBean messageToDownload) {
        this.messageToDownload = messageToDownload;
    }

    private void showElement(boolean show){
        downloadProgress.setVisible(show);
        downloadLabel.setVisible(show);
    }


}
