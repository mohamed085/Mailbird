import View.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class RunApp extends Application {
    ViewFactory viewFactory;

    @Override
    public void start(Stage primaryStage) throws Exception{
        viewFactory = ViewFactory.defaultViewFactory;
        primaryStage.setScene(viewFactory.getLoginScene());
        primaryStage.setTitle("Register");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
