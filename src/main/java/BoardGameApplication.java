import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BoardGameApplication extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/gameUI.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("3x3 Tic Tac Toe");
        stage.setResizable(false);
        stage.show();
    }

}
