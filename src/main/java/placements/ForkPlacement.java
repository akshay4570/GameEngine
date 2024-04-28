package placements;

import boards.TicTacToeBoard;
import game.Cell;
import game.GameInfo;
import game.Player;
import utils.Utils;

import java.util.Optional;

public class ForkPlacement implements Placement {

    private static ForkPlacement forkPlacement;

    private ForkPlacement() {
    }

    public static synchronized Placement get() {
        forkPlacement = (ForkPlacement) Utils.singleTon(forkPlacement, ForkPlacement::new);
        return forkPlacement;
    }

    @Override
    public Optional<Cell> place(TicTacToeBoard board, Player player) {
        Cell best = null;
        GameInfo gameInfo = ruleEngine.getInfo(board);
        if (gameInfo.isHasFork()) {
            best = gameInfo.getForkCell();
        }
        return Optional.ofNullable(best);
    }

    @Override
    public Placement next() {
        return CenterPlacement.get();
    }
}
