package game;

public class GameConfig {
    boolean timed;
    public Integer timePerMove;

    public GameConfig(boolean timed, Integer timePerMove) {
        this.timed = timed;
        this.timePerMove = timePerMove;
    }
}
