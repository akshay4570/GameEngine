package api;

import boards.CellBoard;
import boards.TicTacToeBoard;
import boards.TicTacToeBoard.Symbol;
import game.*;
import placements.DefensivePlacement;
import placements.OffensivePlacement;

import java.util.*;

public class RuleEngine {

    Map<String, RuleSet> rulesMap = new HashMap<>();

    public RuleEngine() {
        rulesMap.put(TicTacToeBoard.class.getName(), TicTacToeBoard.getRules());
    }

    public GameInfo getInfo(CellBoard board) {
        if (board instanceof TicTacToeBoard) {
            GameResult gameState = getState(board);
            TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
            for (Symbol symbol : Symbol.values()) {
                Player player = new Player(symbol.getMarker());
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (ticTacToeBoard.getSymbol(i, j) != null) {
                            TicTacToeBoard b = ticTacToeBoard.move(new Move(new Cell(i, j), player));
                            //Force opponent to make a defensive or offensive move
                            //Check if we can still win with that move
                            DefensivePlacement defensivePlacement = DefensivePlacement.get();
                            Optional<Cell> defensiveCell = defensivePlacement.place(b, player.flip());
                            if (defensiveCell.isPresent()) {
                                b = b.move(new Move(defensiveCell.get(), player.flip()));
                                OffensivePlacement offensivePlacement = OffensivePlacement.get();
                                Optional<Cell> offensiveCell = offensivePlacement.place(b, player);

                                if (offensiveCell.isPresent()) {
                                    return new GameInfoBuilder().isOver(gameState.isOver())
                                            .winner(gameState.getWinner())
                                            .hasFork(true)
                                            .forkCell(new Cell(i, j))
                                            .player(player.flip())
                                            .build();
                                }
                            }
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

