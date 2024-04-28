package game;

public class GameInfo {

    private final Cell forkCell;
    private boolean isOver;
    private String winner;
    private boolean hasFork;

    public Cell getForkCell() {
        return forkCell;
    }

    public boolean isOver() {
        return isOver;
    }

    public String getWinner() {
        return winner;
    }

    public boolean isHasFork() {
        return hasFork;
    }

    public Player getPlayer() {
        return player;
    }

    private Player player;

    public GameInfo(boolean isOver, String winner, boolean hasFork, Player player, Cell forkCell) {
        this.isOver = isOver;
        this.winner = winner;
        this.hasFork = hasFork;
        this.player = player;
        this.forkCell = forkCell;
    }

}

