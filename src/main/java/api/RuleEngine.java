package api;

import boards.TicTacToeBoard;
import game.*;

import java.util.*;

public class RuleEngine {

    Map<String, RuleSet> rulesMap = new HashMap<>();

    public RuleEngine() {
        rulesMap.put(TicTacToeBoard.class.getName(), TicTacToeBoard.getRules());
    }

    public GameInfo getInfo(Board board) {
        if (board instanceof TicTacToeBoard) {
            GameResult gameState = getState(board);
            final String[] players = new String[]{"X", "O"};
            Cell forkCell = null;
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
                                forkCell = new Cell(k, l);
                                b1.move(new Move(forkCell, player.flip()));
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
                                    .forkCell(forkCell)
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

            for (Rule r : rulesMap.get(TicTacToeBoard.class.getName())) {
                GameResult gameState = r.condition.apply(board1);
                if (gameState.isOver()) {
                    return gameState;
                }
            }
            return new GameResult(false, "-");
        } else {
            throw new IllegalArgumentException();
        }
    }
}

