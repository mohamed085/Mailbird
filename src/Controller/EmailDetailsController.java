package Controller;

import Model.EmailMessageBean;
import Model.ModelAccess;
import Services.MessageRenderServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsController extends AbstractController implements Initializable {

	private EmailMessageBean selectedMessage;
	private MessageRenderServices messageRenderServices;

	@FXML
	private Label mailSubject,mailFrom;

	@FXML
	private WebView mailContent;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		messageRenderServices = new MessageRenderServices(mailContent.getEngine());
		EmailMessageBean selectedMessage = getModelAccess().getSelectedMessage();
		mailSubject.setText("Subject: "+ selectedMessage.getSubject());
		mailFrom.setText("From: "+ selectedMessage.getFrom());
		if (selectedMessage != null){
			messageRenderServices.setMessageToRender(selectedMessage);
			messageRenderServices.restart();
		}
	}

	public EmailDetailsController(ModelAccess modelAccess) {
		super(modelAccess);
	}

}
