import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/fxml/StudioLayout.fxml"));

        Scene scene = new Scene(root, 1280, 800);

        primaryStage.setTitle("CipherForge");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/ui/assets/icon.png")));
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1024);
        primaryStage.setMinHeight(680);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
