// CHECKSTYLE:OFF
package stonestactoe.results;

import jakarta.xml.bind.JAXBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ScoresManager {

    private GameScores scoresList = new GameScores();
    private static final Logger scoresLogger = LogManager.getLogger("scoresLog");
    public ScoresManager(){
        File file = new File("game-scores.xml");
        if(file.exists()) {
            try {
                this.scoresList = JAXBHelper.fromXML(GameScores.class, new FileInputStream("game-scores.xml"));
            }
            catch (JAXBException | FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else{
            try {
                if (file.createNewFile()) {
                    scoresLogger.debug("Scores log file created");
                } else {
                    scoresLogger.debug("Scores log file exists");
                }
            } catch (IOException ioException) {

            }
            scoresList.setGameScoreList(new ArrayList<>());
        }
    }

    public void addScore(String player1, String player2, String dateStarted, String winnerName, int moves){
        List<GameResult> scores = scoresList.getGameScoreList();
        System.out.println(player1 + player2 + dateStarted+ winnerName + moves);
        scores.add(new GameResult(player1, player2, dateStarted, winnerName, moves));
        scoresList.setGameScoreList(scores);

        try {
            JAXBHelper.toXML(scoresList, new FileOutputStream("game-scores.xml"));
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public List<GameResult> getGameScoreList() {
        return scoresList.getGameScoreList();
    }
}
