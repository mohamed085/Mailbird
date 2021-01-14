package Controller;

import Model.EmailMessageBean;
import Model.ModelAccess;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsController extends AbstractController implements Initializable {

	private EmailMessageBean selectedMessage;

	@FXML
	private Label mailSubject,mailFrom;

	@FXML
	private WebView mailContent;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectedMessage = getModelAccess().getSelectedMessage();
		mailSubject.setText("Subject: " + selectedMessage.getSubject());
		mailFrom.setText("From: " + selectedMessage.getFrom());
		mailContent.getEngine().loadContent(selectedMessage.getContent());
	}

	public EmailDetailsController(ModelAccess modelAccess) {
		super(modelAccess);
	}

}
