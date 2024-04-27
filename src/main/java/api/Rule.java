package api;

import game.Board;
import game.GameResult;

import java.util.function.Function;

public class Rule<T extends Board> {
    Function<T, GameResult> condition;

    public Rule(Function<T, GameResult> condition) {
        this.condition = condition;
    }
}
