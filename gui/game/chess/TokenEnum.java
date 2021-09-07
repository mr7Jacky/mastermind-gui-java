package hw02.game.chess;

import javafx.scene.paint.Color;

/**
 * A enumerator represent for 6 kind of tokens
 * including blue, green, orange, purple, red, yellow
 * @author Jacky Lin, Yida Chen
 */
public enum TokenEnum {
    /** blue, green, orange, purple, red, yellow tokens */
    B(Color.BLUE),G(Color.GREEN),O(Color.ORANGE),P(Color.PURPLE),R(Color.RED),Y(Color.YELLOW);

    /** name for tokens */
    private Color color;

    /**
     * constructor for the number of the token
     * @author Jacky Lin
     */
    TokenEnum(Color color) {
        this.color = color;
    }

    /**
     * Getter method for the color of tokens
     * @author Yida Chen
     */
    public Color getColor() {
        return color;
    }
}
