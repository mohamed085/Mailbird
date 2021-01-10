import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RunApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/Login.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Login Client App");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
