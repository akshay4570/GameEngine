import api.AIEngine;
import api.GameEngine;
import api.RuleEngine;
import game.Board;
import game.Cell;
import game.Move;
import game.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GamePlayTest {
    GameEngine gameEngine;
    AIEngine aiEngine;
    RuleEngine ruleEngine;

    @Before
    public void setup() {
        gameEngine = new GameEngine();
        aiEngine = new AIEngine();
        ruleEngine = new RuleEngine();
    }

    @Test
    public void checkForRowWin() {

        Board board = gameEngine.start("TicTacToe");

        int firstPlayerMoves[][] = new int[][]{{1, 0}, {1, 1}, {1, 2}};
        int secondPlayerMoves[][] = new int[][]{{0, 0}, {0, 1}, {0, 2}};
        System.out.println(board);
        playGame(board, firstPlayerMoves, secondPlayerMoves);
        Assert.assertTrue(ruleEngine.getState(board).isOver());
        Assert.assertEquals(ruleEngine.getState(board).getWinner(), "X");
    }

    public void playGame(Board board, int[][] firstPlayerMoves, int[][] secondPlayerMoves) {
        int row, col;
        int next = 0;
        while (!ruleEngine.getState(board).isOver()) {
            Player opponent = new Player("X");
            Player computer = new Player("O");
            System.out.println("Make your move!!");
            System.out.println(board);
            row = firstPlayerMoves[next][0];
            col = firstPlayerMoves[next][1];
            Move opponentMove = new Move(new Cell(row, col), opponent);
            gameEngine.move(board, opponentMove);
            if (!ruleEngine.getState(board).isOver()) {
                int sRow = secondPlayerMoves[next][0];
                int sCol = secondPlayerMoves[next][1];
                Move computerMove = new Move(new Cell(sRow, sCol), computer);
                gameEngine.move(board, computerMove);
            }
            next++;
        }
    }
}
