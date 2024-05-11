package api;

import boards.TicTacToeBoard;
import game.*;
import placements.OffensivePlacement;
import placements.Placement;

import java.util.Optional;

public class AIEngine {
    RuleEngine ruleEngine = new RuleEngine();

    public Move suggestMove(Player player, Board board) {
        if (board instanceof TicTacToeBoard) {
            TicTacToeBoard board1 = (TicTacToeBoard) board;
            Cell suggestion;
            int threshold = 4;
            if (countMoves(board1) < threshold) {
                suggestion = getBasicMove(board1);
            } else if (countMoves(board1) < threshold + 1) {
                suggestion = getCellToPlay(player, board1);
            } else {
                suggestion = getOptimalMove(player, board1);
            }
            if (suggestion != null) return new Move(suggestion, player);
            throw new IllegalArgumentException();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Cell getOptimalMove(Player player, TicTacToeBoard board1) {
        /*
         * If You have a winning move play it
         * If opp has a winning move block it
         * If you have a fork then play it
         * If opp has a fork then block it
         *If the center is available take it
         * If the corner is available take it
         * */
        Placement placement = OffensivePlacement.get();
        while (placement.next() != null) {
            Optional<Cell> place = placement.place(board1, player);
            if (place.isPresent()) {
                return place.get();
            }
            placement = placement.next();
        }
        return null;
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

    private Cell getBasicMove(TicTacToeBoard board1) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board1.getCell(i, j) == null) {
                    return new Cell(i, j);
                }
            }
        }
        return null;
    }

    public Cell getCellToPlay(Player player, TicTacToeBoard board1) {

        //Attacking Moves
        Cell best = offense(player, board1);
        if (best != null) return best;

        //Defensive Moves
        best = defense(player, board1);
        if (best != null) return best;

        return getBasicMove(board1);
    }

    private Cell defense(Player player, TicTacToeBoard board1) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board1.getSymbol(i, j) == null) {
                    Move move = new Move(new Cell(i, j), player.flip());
                    TicTacToeBoard boardCopy = (TicTacToeBoard) board1.move(move);
                    if (ruleEngine.getState(boardCopy).isOver()) {
                        return move.getCell();
                    }
                }
            }
        }
        return null;
    }

    private Cell offense(Player player, TicTacToeBoard board1) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board1.getSymbol(i, j) == null) {
                    Move move = new Move(new Cell(i, j), player);
                    TicTacToeBoard boardCopy = (TicTacToeBoard) board1.move(move);
                    if (ruleEngine.getState(boardCopy).isOver()) {
                        return move.getCell();
                    }
                }
            }
        }
        return null;
    }
}
