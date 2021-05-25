package controllers;

import javafx.application.Platform;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import models.BoardGameModel;
public class BoardGameController {

    @FXML
    private GridPane boardID;

    @FXML
    private HBox header;

    private BoardGameModel model = new BoardGameModel();

    private String playerName1;
    private String playerName2;

    public void setPlayerName1(String playerName1) {
        this.playerName1 = playerName1;
    }

    public void setPlayerName2(String playerName2) {
        this.playerName2 = playerName2;
    }


    @FXML
    private void initialize(){
        model = new BoardGameModel();
        for(var i = 0; i < boardID.getRowCount(); i++) {
            for (var j = 0; j < boardID.getColumnCount(); j++) {
                var cell = createCell(i,j);
                    boardID.add(cell, j, i);
            }
        }
    }

    private StackPane createCell(int i, int j) {
        var cell = new StackPane();
        cell.setOnMouseClicked(this::mouseClickHandler);
        var stone = new Circle(60);
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
        if(model.hasFinished()){
            if(model.getMoveCount()%2 == 0){
                System.out.println("Player 2 has Won!");
            }
            else{
                System.out.println("Player 1 has won!");
            }

        }
        System.out.printf("Mouse Clicked On: (%d,%d)\n", row, col);
    }

    @FXML
    private void handleQuitButton(){
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

    }

}
