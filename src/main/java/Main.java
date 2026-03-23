import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/template.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Learning App");
        stage.setResizable(false);
        stage.show();

        root.lookup(".split-pane-divider").setCursor(Cursor.DEFAULT);
    }

    static void main(String[] args) {
        launch(args);
    }
}
