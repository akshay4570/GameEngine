package api;

import boards.TicTacToeBoard;
import game.*;

public class AIEngine {
    public Move suggestMove(Player computer, Board board) {
        if (board instanceof TicTacToeBoard) {
            TicTacToeBoard board1 = (TicTacToeBoard) board;
            Move suggestion;
            int threshold = 4;
            if (countMoves(board1) < threshold) {
                suggestion = getBasicMove(computer, board1);
            } else {
                suggestion = getSmartMove(computer, board1);
            }
            if (suggestion != null) return suggestion;
            throw new IllegalArgumentException();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private int countMoves(TicTacToeBoard board1) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board1.getCell(i, j) != null) {
                    count++;
                }
            }
        }
        return count;
    }

    private Move getBasicMove(Player computer, TicTacToeBoard board1) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board1.getCell(i, j) == null) {
                    return new Move(new Cell(i, j), computer);
                }
            }
        }
        return null;
    }

    public Move getSmartMove(Player player, TicTacToeBoard board1) {
        RuleEngine ruleEngine = new RuleEngine();
        //Attacking Moves
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board1.getSymbol(i, j) == null) {
                    Move move = new Move(new Cell(i, j), player);
                    TicTacToeBoard boardCopy = board1.copy();
                    boardCopy.move(move);
                    if (ruleEngine.getState(boardCopy).isOver()) {
                        return move;
                    }
                }
            }
        }

        //Defensive Moves
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board1.getSymbol(i, j) == null) {
                    Move move = new Move(new Cell(i, j), player.flip());
                    TicTacToeBoard boardCopy = board1.copy();
                    boardCopy.move(move);
                    if (ruleEngine.getState(boardCopy).isOver()) {
                        return move;
                    }
                }
            }
        }
        return getBasicMove(player, board1);
    }
}
