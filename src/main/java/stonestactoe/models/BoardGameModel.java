package stonestactoe.models;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

/**
 * The Model that includes the logic implementation of the game.
 */
public class BoardGameModel {

    /**
     * The game board size.
     * */
    public static final int BOARD_SIZE = 3;

    /**
     * @return an array with read-only view gameBoard that stores {@code Cell} objects.
     */
    public ReadOnlyObjectWrapper<Cell>[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * The game board in which {@code Cell} objects are saved.
     * */
    private ReadOnlyObjectWrapper<Cell>[][] gameBoard = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    /**
     * The number of moves
     * */
    private int moveCount = 0;

    /**
     * The Model's parameterless constructor.
     */
    public BoardGameModel() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                gameBoard[i][j] = new ReadOnlyObjectWrapper<Cell>(Cell.EMPTY);
            }
        }
    }

    /**
     * @param i The row coordinate.
     * @param j The column coordinate.
     * @return a read-only view of the property that represents the state of a specific cell.
     */
    public ReadOnlyObjectProperty<Cell> cellProperty(int i , int j) { return gameBoard[i][j].getReadOnlyProperty(); }

    /**
     * @return The number of moves
     */
    public int getMoveCount() { return moveCount; }

    /**
     * @param i The row coordinate.
     * @param j The column coordinate.
     * Increases the moves and sets coordinates in the 2D Array {@code gameBoard}.
     */
    public void move(int i, int j) {
        moveCount++;
        gameBoard[i][j].set(
                switch (gameBoard[i][j].get()) {
                    case EMPTY -> Cell.FIRST;
                    case FIRST -> Cell.SECOND;
                    case SECOND -> Cell.THIRD;
                    case THIRD ->  Cell.EMPTY;
                }
        );
    }

    /**
     * Specifies if the game has finished its goal of winning.
     * @return {@code true} if any of the goals are reached  {@code false} else.
     */
    public boolean hasFinished(){
        if(checkDiagonal(gameBoard) || checkHorizontal(gameBoard) || checkVertical(gameBoard))
            return true;
        return false;
    }

    /**
     * @param gameBoard The game board in which our {@code Cell} objects are stored.
     * @return {@code true} if the conditions if diagonal crosses are met  {@code false} else.
     */
    public boolean checkDiagonal(ReadOnlyObjectWrapper<Cell>[][] gameBoard) {
        if (gameBoard[1][1].getValue() != Cell.EMPTY) {
            if (gameBoard[0][0].getValue() == gameBoard[1][1].getValue() && gameBoard[1][1].getValue() == gameBoard[2][2].getValue()) {
                return true;
            }
            if (gameBoard[0][2].getValue() == gameBoard[1][1].getValue() && gameBoard[1][1].getValue() == gameBoard[2][0].getValue()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param gameBoard The game board in which our {@code Cell} objects are stored.
     * @return {@code true} if the corresponding cells vertically are filled  {@code false} else.
     */
    public boolean checkVertical(ReadOnlyObjectWrapper<Cell>[][] gameBoard) {
        int count;
        for(var j = 0; j < BOARD_SIZE ;j++){
            count = 0;
            for(var i = 0 ; i < BOARD_SIZE - 1; i++){
                ReadOnlyObjectWrapper<Cell> currentCell = gameBoard[i][j];
                ReadOnlyObjectWrapper<Cell> nextCell = gameBoard[i+1][j];
                if(currentCell.getValue() != Cell.EMPTY && currentCell.getValue() == nextCell.getValue()){
                    count++;
                }
            }
            if(count==2){
                return true;
            }
        }
        return false;
    }

    /**
     * @param gameBoard The game board in which our {@code Cell} objects are stored.
     * @return {@code true} if the corresponding cells horizontally are filled  {@code false} else.
     */
    public boolean checkHorizontal(ReadOnlyObjectWrapper<Cell>[][] gameBoard){
        int count = 0;
        for(var i = 0; i < BOARD_SIZE;i++){
            count = 0;
            for(var j = 0 ; j < BOARD_SIZE - 1 ; j++){
                ReadOnlyObjectWrapper<Cell> currentCell = gameBoard[i][j];
                ReadOnlyObjectWrapper<Cell> nextCell = gameBoard[i][j+1];
                if(currentCell.getValue() != Cell.EMPTY && currentCell.getValue() == nextCell.getValue()){
                    count++;
                }
            }
            if(count==2){
                return true;
            }
        }
        return false;
    }
}
