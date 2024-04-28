package api;

import boards.CellBoard;
import game.GameResult;

import java.util.function.Function;

public class Rule {
    Function<CellBoard, GameResult> condition;

    public Rule(Function<CellBoard, GameResult> condition) {
        this.condition = condition;
    }
}
