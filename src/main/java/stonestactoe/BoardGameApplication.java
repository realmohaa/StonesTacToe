package stonestactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BoardGameApplication extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/start.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Stones Tac-Toe");
        stage.setResizable(false);
        stage.show();
    }
}
