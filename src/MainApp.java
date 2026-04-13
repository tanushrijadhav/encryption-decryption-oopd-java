import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Load FXML layout from resources
        Parent root = FXMLLoader.load(
                getClass().getResource("/ui/fxml/StudioLayout.fxml")
        );

        // Create scene with root node and set dimensions
        Scene scene = new Scene(root, 1280, 800);

        // Set window title
        primaryStage.setTitle("CipherForge");

        // Set application icon from resources
        primaryStage.getIcons().add(
                new Image(getClass().getResourceAsStream("/ui/assets/icon.png"))
        );

        // Attach scene to stage
        primaryStage.setScene(scene);

        // Set minimum window width
        primaryStage.setMinWidth(1024);

        // Set minimum window height
        primaryStage.setMinHeight(680);

        // Display the stage (launch UI)
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch JavaFX application
        launch(args);
    }
}