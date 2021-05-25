package controllers;

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
import results.GameResult;

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
    public void handlePlayButton(ActionEvent actionEvent) throws IOException {
        if(playerTwoTF.getText().isBlank() || playerOneTF.getText().isBlank()){
            missingPlayersLabel.setText("Please Enter both Player names");
        }
        else{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/gameUI.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<BoardGameController>getController().setFirstPlayer(playerOneTF.getText());
            fxmlLoader.<BoardGameController>getController().setSecondPlayer(playerTwoTF.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
