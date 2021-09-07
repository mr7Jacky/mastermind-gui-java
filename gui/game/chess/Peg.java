/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Yida Chen
 * Section: CSCI 205 9:00 AM
 * Date: 10/26/19
 * Time: 3:28 PM
 *
 * Project: csci205_hw
 * Package: hw02.game
 * Class: Peg
 *
 * Description:
 *
 * ****************************************
 */
package hw02.game.chess;

import javafx.scene.paint.Color;

/**
 * The Peg class represent the result peg of the Mastermind game.
 * @authro Yida Chen
 */
public class Peg {
    /** the color of the correct peg*/
    private Color starPegColor;
    /** the color of the correct color but not in the right position peg*/
    private Color plusPegColor;
    /** the wrong color of the peg*/
    private Color pegOffColor;
    /** the current color of the peg*/
    private Color currentColor;

    /**
     * The constructor of the Peg class
     * @author Yida Chen
     */
    public Peg() {
        this.starPegColor = PegEnum.CORRECT.getColor();
        this.plusPegColor = PegEnum.GOODCOLOR.getColor();
        this.pegOffColor = PegEnum.WRONG.getColor();
        this.currentColor = this.pegOffColor;
    }

    /**
     * Show the peg as a star peg, which means the token with correct color at the correct spot.
     * @author Yida Chen
     */
    public void showStarPeg(){
        this.setCurrentColor(this.starPegColor);
    }

    /**
     * Show the peg as a plus peg, which means the token with correct color but at the wrong spot.
     * @author Yida Chen
     */
    public void showPlusPeg(){ this.setCurrentColor(this.plusPegColor); }

    /**
     * Getter for the current color of the peg.
     * @return Color object represent the current color of the peg.
     * @author Yida Chen
     */
    public Color getCurrentColor() {
        return currentColor;
    }

    /**
     * Setter for the current color of the peg
     * @param color Color to set on the peg as its current color.
     * @author Yida Chen
     */
    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }
}
