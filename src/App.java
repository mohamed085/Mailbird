import View.ViewFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ViewFactory viewFactory = ViewFactory.defaultFactory;
		Scene scene = viewFactory.getLoginScene();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.show();
	}

}