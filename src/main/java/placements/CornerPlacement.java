package placements;

import boards.TicTacToeBoard;
import game.Cell;
import game.Player;
import utils.Utils;

import java.util.Optional;

public class CornerPlacement implements Placement {

    private static CornerPlacement cornerPlacement;

    private CornerPlacement() {
    }

    public static synchronized Placement get() {
        cornerPlacement = (CornerPlacement) Utils.singleTon(cornerPlacement, CornerPlacement::new);
        return cornerPlacement;
    }

    @Override
    public Optional<Cell> place(TicTacToeBoard board, Player player) {
        return Optional.ofNullable(cornerPlacement(board));
    }

    @Override
    public Placement next() {
        return null;
    }

    private Cell cornerPlacement(TicTacToeBoard board) {
        final int[][] corners = new int[][]{{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        for (int i = 0; i < 4; i++) {
            if (board.getSymbol(corners[i][0], corners[i][1]) == null) {
                return new Cell(corners[i][0], corners[i][1]);
            }
        }
        return null;
    }
}
