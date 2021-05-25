package models;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class BoardGameModel {

    public static final int BOARD_SIZE = 3;

    public int getMoveCount() {
        return moveCount;
    }

    private int moveCount = 0;

    private ReadOnlyObjectWrapper<Cell>[][] gameBoard = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];

    private ReadOnlyIntegerWrapper stoneSteps = new ReadOnlyIntegerWrapper();

    public BoardGameModel() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                gameBoard[i][j] = new ReadOnlyObjectWrapper<Cell>(Cell.EMPTY);
            }
        }
    }

    public ReadOnlyObjectProperty<Cell> cellProperty(int i , int j) { return gameBoard[i][j].getReadOnlyProperty(); }

    public Cell getCell(int i, int j) { return gameBoard[i][j].get(); }

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

    public String toString() {
        StringBuilder myStringBuilder = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                myStringBuilder.append(gameBoard[i][j].get().ordinal()).append(' ');
            }
            myStringBuilder.append('\n');
        }
        return myStringBuilder.toString();
    }

    public static void main(String[] args) {
        var model = new BoardGameModel();
    }

    public boolean hasFinished(){
        if(checkDiagonal(gameBoard) || checkHorizontal(gameBoard) || checkVertical(gameBoard))
            return true;

        return false;
    }

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
