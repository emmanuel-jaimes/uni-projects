
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.util.Objects;

/**
 * QuizGUIApp the driver class that initializes GUI for user to interact with
 */
public class QuizGUIApp extends Application
{
    /**
     * @param args initialize driver
     */
    public static void main (String[] args)
    {
        launch(args);
    }

    /**
     * @param stage display area for GUI components
     * @throws Exception if unable to start
     */
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxml = new FXMLLoader (Objects.requireNonNull(getClass().getResource("QuizGUI.fxml")));
        fxml.setRoot(new GridPane());
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        stage.setTitle("Quiz GUI");
        stage.setScene(scene);
        stage.show();
    }
}
