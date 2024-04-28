package boards;

import game.Board;

public interface CellBoard extends Board {
    String getSymbol(int i, int j);
}
