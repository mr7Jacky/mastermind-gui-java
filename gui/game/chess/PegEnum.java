package hw02.game.chess;

import javafx.scene.paint.Color;

/**
 * A enumerator represent for 3 kind of peg each indicate the status of tokens
 * CORRECT stands for the right token in the right position
 * GOODCOLOR stands for the right token not in the right position
 * WRONG stands for the wrong token
 *
 * @author Jacky Lin, Yida Chen
 */
public enum PegEnum {
    /** C, U, E pegs*/
    CORRECT(Color.BLACK), GOODCOLOR(Color.WHITE), WRONG(Color.GRAY);

    /** the value of the C, U ,E pegs, for their shown priority*/
    private Color color;

    /**
     * the constructor for the Peg {@link PegEnum} to initialized the name of pegs
     * @author Jacky Lin
     */
    PegEnum(Color color) {
        this.color = color;
    }

    /**
     * Getter for the color of the PegEnum
     * @return Color object represent the color of the peg
     * @author Yida Chen
     */
    public Color getColor() {
        return color;
    }
}
