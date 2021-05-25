// CHECKSTYLE:OFF
package stonestactoe.controllers;

import javafx.application.Platform;
import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import stonestactoe.models.BoardGameModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stonestactoe.results.ScoresManager;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class BoardGameController {
    @FXML
    private GridPane boardID;

    @FXML
    private Label winnerLabel;

    private BoardGameModel model = new BoardGameModel();

    private String firstPlayer;
    private String secondPlayer;
    private String winnerName = "";

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public void setSecondPlayer(String secondPlayer) {
        this.secondPlayer = secondPlayer;
    }
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
    private static final Logger logger = LogManager.getLogger("gameLogger");

    @FXML
    private void initialize(){
        logger.info("initializing the board");
        model = new BoardGameModel();
        for(var i = 0; i < boardID.getRowCount(); i++) {
            for (var j = 0; j < boardID.getColumnCount(); j++) {
                var cell = createCell(i,j);
                    boardID.add(cell, j, i);
            }
        }

        Platform.runLater(() -> winnerLabel.setText(firstPlayer + " Vs. " + secondPlayer));
        logger.info("Finished game initialization");
        logger.info("Game has started");
    }

    private StackPane createCell(int i, int j) {
        var cell = new StackPane();
        cell.setOnMouseClicked(this::mouseClickHandler);
        var stone = new Circle(50);
        cell.getStyleClass().add("cell");
        stone.getStyleClass().add("stone");

        stone.fillProperty().bind(
                new ObjectBinding<Paint>() {
                    {
                        super.bind(model.cellProperty(i,j));
                    }
                    @Override
                    protected Paint computeValue() {
                        return switch (model.cellProperty(i,j).get()) {
                            case EMPTY -> Color.TRANSPARENT;
                            case FIRST -> Color.RED;
                            case SECOND -> Color.YELLOW;
                            case THIRD -> Color.GREEN;
                        };
                    }
                }
        );

        cell.getChildren().add(stone);
        return cell;
    }

    @FXML
    private void mouseClickHandler(MouseEvent event) {
        var cell = (Pane) event.getSource();
        var row = GridPane.getRowIndex(cell);
        var col = GridPane.getColumnIndex(cell);
        model.move(row,col);
        logger.info("Mouse Clicked On: ({},{})\n", row, col);
        if(model.hasFinished()){
            if(model.getMoveCount()%2 == 0){
                winnerName = secondPlayer;
                logger.info("{} has won the game", winnerName);
                Platform.runLater(() -> winnerLabel.setText(winnerName + " has won the game!"));
            }
            else{
                winnerName = firstPlayer;
                logger.info("{} has won the game", winnerName);
                Platform.runLater(() -> winnerLabel.setText(winnerName + " has won the game!"));
            }
        }
    }

    @FXML
    private void handleQuitButton(){
        logger.debug("The game has been closed");
        Platform.exit();
    }

    @FXML
    private void handleGameScores(ActionEvent actionEvent) throws IOException {
        createResult();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/scoreUI.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleResetGame(ActionEvent actionEvent){
        resetBoardCells();
        initialize();
    }

    public void resetBoardCells(){
        for(Node node : boardID.getChildren()){
            StackPane stkPane = (StackPane) node;
            for(int i = 0 ; i < stkPane.getChildren().size();i++){
                stkPane.getChildren().remove(i);
            }
        }
        logger.warn("Game is resetting");
    }

    private void createResult(){
        new ScoresManager().addScore(firstPlayer,secondPlayer, ZonedDateTime.now().format(dateTimeFormatter),winnerName, model.getMoveCount());
    }

}
