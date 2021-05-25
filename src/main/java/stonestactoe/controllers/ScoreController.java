// CHECKSTYLE:OFF
package stonestactoe.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import stonestactoe.results.GameResult;
import stonestactoe.results.ScoresManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreController {

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<GameResult,String> firstPlayerCol;

    @FXML
    private TableColumn<GameResult,String> secondPlayerCol;

    @FXML
    private TableColumn<GameResult,String> winnerCol;

    @FXML
    private TableColumn<GameResult,Integer> movesCol;

    @FXML
    private TableColumn<GameResult,String> dateCol;

    @FXML
    private void initialize(){
        firstPlayerCol.setCellValueFactory(new PropertyValueFactory<>("player1"));
        secondPlayerCol.setCellValueFactory(new PropertyValueFactory<>("player2"));
        movesCol.setCellValueFactory(new PropertyValueFactory<>("moves"));
        winnerCol.setCellValueFactory(new PropertyValueFactory<>("winnerName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateStarted"));
        List<GameResult> gameResultsList = new ScoresManager().getGameScoreList();
        List<GameResult> top5List = getTop5(gameResultsList);
        ObservableList<GameResult> observableList = FXCollections.observableArrayList(top5List);
        tableView.setItems(observableList);
    }

    @FXML
    public void HandleHomeButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/start.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private List<GameResult> getTop5(List<GameResult> gameResultsList){
        Map<String,Integer> winnerCount = new HashMap<>();
        for(GameResult result : gameResultsList){
            if(winnerCount.containsKey(result.getWinnerName()))
                winnerCount.put(result.getWinnerName(),winnerCount.get(result.getWinnerName()) + 1);
            else{
                winnerCount.put(result.getWinnerName(),1);
            }
        }

        return gameResultsList.stream()
                .sorted((a,b) -> Integer.compare(winnerCount.get(b.getWinnerName()),winnerCount.get(a.getWinnerName())))
                .distinct()
                .limit(5)
                .collect(Collectors.toList());
    }

}
