import api.AIEngine;
import api.GameEngine;
import api.RuleEngine;
import game.Board;
import game.Cell;
import game.Move;
import game.Player;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameEngine gameEngine = new GameEngine();
        AIEngine aiEngine = new AIEngine();
        RuleEngine ruleEngine = new RuleEngine();
        Board board = gameEngine.start("TicTacToe");
        System.out.println(board);
        int row, col;
        Scanner sc = new Scanner(System.in);
        while (!ruleEngine.getState(board).isOver()) {
            Player opponent = new Player("X");
            Player computer = new Player("O");
            System.out.println("Make your move!!");
            System.out.println(board);
            row = sc.nextInt();
            col = sc.nextInt();
            Move opponentMove = new Move(new Cell(row, col), opponent);
            gameEngine.move(board, opponentMove);
            if (!ruleEngine.getState(board).isOver()) {
                Move computerMove = aiEngine.suggestMove(computer, board);
                gameEngine.move(board, computerMove);
            }
        }
        System.out.println("Game Result : " + ruleEngine.getState(board));
    }
}
