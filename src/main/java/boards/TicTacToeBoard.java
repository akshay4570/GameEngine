package boards;

import api.Rule;
import api.RuleSet;
import game.Cell;
import game.GameResult;
import game.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TicTacToeBoard implements CellBoard {
    String cells[][] = new String[3][3];
    History history = new History();

    public static RuleSet getRules() {
        RuleSet rules = new RuleSet();
        rules.add(new Rule(board -> isVictory((i) -> board.getSymbol(i, 0), (i, j) -> board.getSymbol(i, j))));
        rules.add(new Rule(board -> isVictory((i) -> board.getSymbol(0, i), (i, j) -> board.getSymbol(j, i))));
        rules.add(new Rule(board -> isVictoryDiagonal((i) -> board.getSymbol(i, i))));
        rules.add(new Rule(board -> isVictoryDiagonal((i) -> board.getSymbol(i, 2 - i))));
        rules.add(new Rule(board -> {
            int countOfFilledCells = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.getSymbol(i, j) != null) {
                        countOfFilledCells++;
                    }
                }
            }

            if (countOfFilledCells == 9) {
                return new GameResult(true, "-");
            }
            return new GameResult(false, "-");
        }));
        return rules;
    }

    public static GameResult isVictory(Function<Integer, String> startsWith, BiFunction<Integer, Integer, String> next) {
        for (int i = 0; i < 3; i++) {
            boolean possibleStreak = true;
            for (int j = 0; j < 3; j++) {
                if (next.apply(i, j) == null || !next.apply(i, 0).equals(next.apply(i, j))) {
                    possibleStreak = false;
                    break;
                }
            }
            if (possibleStreak) {
                return new GameResult(true, next.apply(i, 0));
            }
        }
        return new GameResult(false, "-");
    }

    public static GameResult isVictoryDiagonal(Function<Integer, String> startsWith) {

        boolean possibleStreak = true;
        for (int i = 0; i < 3; i++) {
            if (startsWith.apply(i) == null || !startsWith.apply(0).equals(startsWith.apply(i))) {
                possibleStreak = false;
                break;
            }
        }
        if (possibleStreak) {
            return new GameResult(true, startsWith.apply(0));
        }

        return new GameResult(false, "-");
    }

    public String getCell(int row, int col) {
        return cells[row][col];
    }

    public void setCell(Cell cell, String symbol) {
        if (cells[cell.getRow()][cell.getCol()] == null) {
            cells[cell.getRow()][cell.getCol()] = symbol;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String getSymbol(int row, int col) {
        return getCell(row, col);
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result += cells[i][j] == null ? "_" : cells[i][j];
            }
            result += "\n";
        }
        return result;
    }

    @Override
    public TicTacToeBoard move(Move move) {
        history.add(new Representation(this));//Made History object light by using Flyweight Design Pattern
        TicTacToeBoard board = copy();
        board.setCell(move.getCell(), move.getPlayer().symbol());
        return board;
    }

    @Override
    public TicTacToeBoard copy() {
        TicTacToeBoard ticTacToeBoard = new TicTacToeBoard();
        for (int i = 0; i < 3; i++) {
            System.arraycopy(cells[i], 0, ticTacToeBoard.cells[i], 0, 3);
        }
        ticTacToeBoard.history = history;
        return ticTacToeBoard;
    }

    public enum Symbol {
        X("X"), O("O");
        String marker;

        Symbol(String marker) {
            this.marker = marker;
        }

        public String getMarker() {
            return marker;
        }
    }
}

class History {
    List<Representation> boards = new ArrayList<>();

    public Representation getBoardAtMove(int moveIndex) {
        for (int i = 0; i < boards.size() - (moveIndex + 1); i++) {
            boards.remove(boards.size() - 1);
        }
        return boards.get(moveIndex);
    }

    public Representation undo() {
        if (boards.isEmpty()) {
            throw new IllegalStateException();
        }
        boards.remove(boards.size() - 1);
        return boards.get(boards.size() - 1);
    }

    public void add(Representation representation) {
        boards.add(representation);
    }
}

class Representation {
    String representation;

    public Representation(TicTacToeBoard board) {
        representation = board.toString();
    }
}