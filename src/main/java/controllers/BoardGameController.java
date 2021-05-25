package controllers;

import javafx.application.Platform;
import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import models.BoardGameModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BoardGameController {
    @FXML
    private GridPane boardID;

    @FXML
    private HBox header;

    @FXML
    private Label winnerLabel;

    private BoardGameModel model = new BoardGameModel();

    private String firstPlayer;
    private String secondPlayer;

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }
    public void setSecondPlayer(String secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    private static final Logger logger = LogManager.getLogger("Main");

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
        var stone = new Circle(40);
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
                logger.info("{} has won the game", secondPlayer);
                Platform.runLater(() -> winnerLabel.setText(secondPlayer + " has won the game!"));
            }
            else{
                logger.info("{} has won the game", firstPlayer);
                Platform.runLater(() -> winnerLabel.setText(firstPlayer + " has won the game!"));
            }
        }
    }

    @FXML
    private void handleQuitButton(){
        logger.debug("The game has been closed");
        Platform.exit();
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

}
