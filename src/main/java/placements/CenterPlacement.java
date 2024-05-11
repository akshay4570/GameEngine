package placements;

import boards.TicTacToeBoard;
import game.Cell;
import game.Player;
import utils.Utils;

import java.util.Optional;

public class CenterPlacement implements Placement {

    private static CenterPlacement centerPlacement;

    private CenterPlacement() {
    }

    public static synchronized CenterPlacement get() {
        centerPlacement = (CenterPlacement) Utils.singleTon(centerPlacement, CenterPlacement::new);
        return centerPlacement;
    }

    @Override
    public Optional<Cell> place(TicTacToeBoard board, Player player) {
        Cell center = null;
        if (board.getSymbol(1, 1) == null) {
            center = new Cell(1, 1);
        }
        return Optional.ofNullable(center);
    }

    @Override
    public Placement next() {
        return CornerPlacement.get();
    }
}
