package api;

import boards.TicTacToeBoard;
import game.*;

import java.util.function.BiFunction;
import java.util.function.Function;

public class RuleEngine {

    public GameInfo getInfo(Board board) {
        if (board instanceof TicTacToeBoard) {
            GameResult gameState = getState(board);
            final String[] players = new String[]{"X", "O"};
            for (int index = 0; index < players.length; index++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        Board b = board.copy();
                        Player player = new Player(players[index]);
                        b.move(new Move(new Cell(i, j), player));
                        boolean canStillWin = false;
                        for (int k = 0; k < 3; k++) {
                            for (int l = 0; l < 3; l++) {
                                Board b1 = board.copy();
                                b1.move(new Move(new Cell(k, l), player.flip()));
                                if (getState(b1).getWinner().equals(player.flip().symbol())) {
                                    canStillWin = true;
                                    break;
                                }
                            }
                            if (canStillWin) {
                                break;
                            }
                        }
                        if (canStillWin) {
                            return new GameInfoBuilder().isOver(gameState.isOver())
                                    .winner(gameState.getWinner())
                                    .hasFork(true)
                                    .player(player.flip())
                                    .build();
                        }
                    }
                }
            }
            return new GameInfoBuilder().isOver(gameState.isOver())
                    .winner(gameState.getWinner())
                    .hasFork(false)
                    .player(new Player("-"))
                    .build();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public GameResult getState(Board board) {
        if (board instanceof TicTacToeBoard) {
            TicTacToeBoard board1 = (TicTacToeBoard) board;

            GameResult rowWin = isVictory((i) -> board1.getSymbol(i, 0), (i, j) -> board1.getSymbol(i, j));
            if (rowWin != null) return rowWin;

            GameResult colWin = isVictory((i) -> board1.getSymbol(0, i), (i, j) -> board1.getSymbol(j, i));
            if (colWin != null) return colWin;

            GameResult diagWin = isVictoryDiagonal((i) -> board1.getSymbol(i, i));
            if (diagWin != null) return diagWin;

            GameResult revDiagWin = isVictoryDiagonal((i) -> board1.getSymbol(i, 2 - i));
            if (revDiagWin != null) return revDiagWin;

            int countOfFilledCells = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board1.getCell(i, j) != null) {
                        countOfFilledCells++;
                    }
                }
            }

            if (countOfFilledCells == 9) {
                return new GameResult(true, "-");
            }
            return new GameResult(false, "-");

        } else {
            return new GameResult(false, "-");
        }
    }

    public GameResult isVictory(Function<Integer, String> startsWith, BiFunction<Integer, Integer, String> next) {
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
        return null;
    }

    public GameResult isVictoryDiagonal(Function<Integer, String> startsWith) {

        boolean possibleStreak = true;
        for (int i = 0; i < 3; i++) {
            if (startsWith.apply(i) == null || !startsWith.apply(0).equals(startsWith.apply(j))) {
                possibleStreak = false;
                break;
            }
        }
        if (possibleStreak) {
            return new GameResult(true, startsWith.apply(0));
        }

        return null;
    }
}

