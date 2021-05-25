package stonestactoe.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardGameModelTest {

    @Test
    void cellProperty() {
        BoardGameModel model = new BoardGameModel();
        assertThrows(IndexOutOfBoundsException.class,() -> {
            model.cellProperty(model.BOARD_SIZE, model.BOARD_SIZE);
        });
        assertEquals(Cell.EMPTY, model.cellProperty(model.BOARD_SIZE-1,model.BOARD_SIZE-1).getValue());
    }

    @Test
    void getMoveCount() {
        BoardGameModel model = new BoardGameModel();
        assertEquals(0,model.getMoveCount());
        model.move(0,0);
        model.move(0,1);
        model.move(0,2);
        assertEquals(3,model.getMoveCount());
    }

    @Test
    void move() {
        BoardGameModel model = new BoardGameModel();
        model.move(2,2);
        assertEquals(model.cellProperty(2,2).getValue(),Cell.FIRST);
        model.move(2,2);
        assertEquals(model.cellProperty(2,2).getValue(),Cell.SECOND);
    }

    @Test
    void hasFinished() {
        BoardGameModel model = new BoardGameModel();
        assertEquals(0,model.getMoveCount());
        model.move(0,0);
        model.move(0,1);
        model.move(0,2);
        assertTrue(model.hasFinished());
    }

    @Test
    void checkVertical() {
        BoardGameModel model = new BoardGameModel();
        assertFalse(model.checkVertical(model.getGameBoard()));
        model.move(0,0);
        model.move(1,0);
        assertFalse(model.checkVertical(model.getGameBoard()));
        model.move(2,0);
        model.move(0,0);
        model.move(1,0);
        model.move(2,0);
        assertTrue(model.checkVertical(model.getGameBoard()));
    }

    @Test
    void checkHorizontal() {
        BoardGameModel model = new BoardGameModel();
        assertFalse(model.checkHorizontal(model.getGameBoard()));
        model.move(0,0);
        model.move(0,1);
        model.move(0,2);
        assertTrue(model.checkHorizontal(model.getGameBoard()));
    }

}
