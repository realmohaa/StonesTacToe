// CHECKSTYLE:OFF
package stonestactoe.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class StartController {
    @FXML
    TextField playerOneTF;
    @FXML
    TextField playerTwoTF;
    @FXML
    Button playBtn;
    @FXML
    Label missingPlayersLabel;
    @FXML
    FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    public void handlePlayButton(ActionEvent actionEvent) throws IOException {
        if(playerTwoTF.getText().isBlank() || playerOneTF.getText().isBlank()){
            missingPlayersLabel.setText("Please Enter both Player names");
        }
        else{
            fxmlLoader.setLocation(getClass().getResource("/fxml/gameUI.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = fxmlLoader.load();
            fxmlLoader.<BoardGameController>getController().setFirstPlayer(playerOneTF.getText());
            fxmlLoader.<BoardGameController>getController().setSecondPlayer(playerTwoTF.getText());
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    public void openSheet(ActionEvent actionEvent) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/scoreUI.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
