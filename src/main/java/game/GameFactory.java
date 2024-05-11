package game;

import boards.TicTacToeBoard;

public class GameFactory {
    public Game createGame(Integer maxTimePerMove, Integer maxTimePerPlayer) {
        return new Game(new GameConfig(maxTimePerPlayer != null, maxTimePerMove),
                new TicTacToeBoard(),
                null,
                0,
                maxTimePerPlayer,
                maxTimePerMove);
    }

    public Game createGame(Integer maxTimePerMove, Integer maxTimePerPlayer, TicTacToeBoard board) {
        return new Game(new GameConfig(maxTimePerPlayer != null, maxTimePerMove),
                board,
                null,
                0,
                maxTimePerPlayer,
                maxTimePerMove);
    }
}
