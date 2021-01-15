package View;

import javax.naming.OperationNotSupportedException;

import Controller.*;
import Model.ModelAccess;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class ViewFactory {

	public static ViewFactory defaultFactory = new ViewFactory();

	private final String LOGIN_LAYOUT = "Login.fxml";
	private final String REGISTER_LAYOUT = "Register.fxml";
	private final String ADD_NEW_ACCOUNT_LAYOUT = "AddNewAccount.fxml";
	private final String MAIN_SCREEN_FXML = "Main.fxml";
	private final String EMAIL_DETAILS_FXML = "EmailDetails.fxml";
	private final String COMPOSE_SCREEN_FXML = "ComposeMessage.fxml";
	
	private ModelAccess modelAccess = new ModelAccess();

	private AbstractController addNewAccountController;
	private AbstractController loginController;
	private AbstractController registerController;
	private AbstractController mainController;
	private AbstractController emailDetailsController;
	private AbstractController composeMessageController;



	private Scene initializeScene(String FXMLPath, AbstractController controller){
		FXMLLoader loader;
		Parent parent;
		Scene scene;
		try {
			loader = new FXMLLoader(getClass().getResource(FXMLPath));
			loader.setController(controller);
			parent = loader.load();
		}catch (Exception exception){
			return null;
		}
		scene = new Scene(parent);
		return scene;
	}

	public Scene getLoginScene() throws IOException {
		loginController = new LoginController(modelAccess);
		return initializeScene(LOGIN_LAYOUT,loginController);
	}

	public Scene getRegisterScene() throws IOException {
		registerController = new RegisterController(modelAccess);
		return initializeScene(REGISTER_LAYOUT,registerController);
	}

	public Scene getAddNewAccount() throws IOException {
		addNewAccountController = new AddNewAccountController(modelAccess);
		return initializeScene(ADD_NEW_ACCOUNT_LAYOUT,addNewAccountController);
	}

	public Scene getMainScene() throws IOException {
		mainController = new MainController(modelAccess);
		return initializeScene(MAIN_SCREEN_FXML,mainController);
	}

	public Scene getEmailDetailsScene(){
		emailDetailsController = new EmailDetailsController(modelAccess);
		return initializeScene(EMAIL_DETAILS_FXML,emailDetailsController);
	}

	public Button setReadStyle(Button button, boolean isRead) {
		if (isRead)
			button.setStyle(" -fx-background-color: #aeafaf;\n" + "  -fx-background-radius: 5em;\n" + "  -fx-min-width: 7px;\n" + "  -fx-min-height: 7px;\n" + "  -fx-max-width: 7px;\n" + "  -fx-max-height: 7px;");
		else
			button.setStyle(" -fx-background-color: #007a17;\n" + "  -fx-background-radius: 5em;\n" + "  -fx-min-width: 10px;\n" + "  -fx-min-height: 10px;\n" + "  -fx-max-width: 10px;\n" + "  -fx-max-height: 10px;");
		return button;
	}

	public Button setStarredStyle(Button button, boolean isRead) {
		if (isRead) {
			button.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.STAR_ALT , "15"));
			button.setStyle(" -fx-background-color: #aeafaf;\n" + "  -fx-min-width: 5px;\n" + "  -fx-min-height: 5px;\n" + "  -fx-max-width: 5px;\n" + "  -fx-max-height: 5px;");
		} else {
			button.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.STAR , "15"));
			button.setStyle(" -fx-background-color: #b0a700;\n" + "  -fx-min-width: 5px;\n" + "  -fx-min-height: 5px;\n" + "  -fx-max-width: 5px;\n" + "  -fx-max-height: 5px;");
		}
		return button;
	}

	public Node resolveIcon(String treeItemValue){
		String lowerCaseTreeItemValue = treeItemValue.toLowerCase();
		ImageView returnIcon;
		try {
			if(lowerCaseTreeItemValue.contains("inbox")){
				returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/inbox.png")));
			} else if(lowerCaseTreeItemValue.contains("sent")){
				returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/sent.png")));
			} else if(lowerCaseTreeItemValue.contains("spam")){
				returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/spam.png")));
			} else if(lowerCaseTreeItemValue.contains("@")) {
				returnIcon = new ImageView(new Image(getClass().getResourceAsStream("Images/email.png")));
			} else if(lowerCaseTreeItemValue.contains("important")){
				returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/important.png")));
			} else if(lowerCaseTreeItemValue.contains("drafts")){
				returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/drafts.png")));
			}else if(lowerCaseTreeItemValue.contains("trash")){
				returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/trash.png")));
			}else if(lowerCaseTreeItemValue.contains("starred")){
				returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/starred.png")));
			}else if(lowerCaseTreeItemValue.contains("all mail")){
				returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/allMail.png")));
			}else{
				returnIcon= new ImageView(new Image(getClass().getResourceAsStream("Images/folder.png")));
			}
		} catch (NullPointerException e) {
			System.out.println("Invalid image location!!!");
			e.printStackTrace();
			returnIcon = new ImageView();
		}

		returnIcon.setFitHeight(16);
		returnIcon.setFitWidth(16);

		return returnIcon;
	}

}
