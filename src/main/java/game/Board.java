package game;

public interface Board {

    Board move(Move move);

    Board copy();
}
