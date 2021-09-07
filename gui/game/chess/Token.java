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
 * Class: Token
 *
 * Description:
 *
 * ****************************************
 */
package hw02.game.chess;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Token class represent the guess token in the mastermind game.
 * @author Yida Chen
 */
public class Token {
    /** the boolean property indicate whether the token have been selected or not*/
    private SimpleBooleanProperty isSelected;
    /** the selected color for the token*/
    private Paint selectedColor;
    /** the deselected color for token*/
    private Paint deselectedColor;

    /**
     * Constructor of the Token class
     * @param selectedColor Color of the Token selected by the user
     * @author Yida Chen
     */
    public Token(Paint selectedColor){
        this.isSelected = new SimpleBooleanProperty(false);
        this.selectedColor = selectedColor;
        this.deselectedColor = Color.GRAY;
    }

    /**
     * Setter for the selectedColor of the Token
     * @param selectedColor Color to set on the Token as the selected color.
     * @author Yida Chen
     */
    public void setSelectedColor(Paint selectedColor) {
        this.selectedColor = selectedColor;
    }

    /**
     * Toggle the token and show the color of the token selected by the user
     * @author Yida Chen
     */
    public void toggleToken(){
        isSelected.set(!isSelected.get());
    }

    /**
     * Getter for the boolean value of the isSelected property
     * @return true if the token is selected, or false if the token is not selected
     * @author Yida Chen
     */
    public boolean isSelected() {
        return isSelected.get();
    }

    /**
     * Return the Boolean property of the isSelected property of the Token object
     * @return Boolean property of the isSelected property of the Token object
     * @author Yida Chen
     */
    public SimpleBooleanProperty isSelectedProperty() {
        return isSelected;
    }

    /**
     * Getter for the current selected color of the Token object.
     * @return Paint object which represents the selected color of the Token object
     * @author Yida Chen
     */
    public Paint getSelectedColor() {
        return this.selectedColor;
    }

    /**
     * Getter for the color of Token object when it is not selected.
     * @return Paint object which represents the color of Token object when it is not selected
     * @author Yida Chen
     */
    public Paint getDeselectedColor() {
        return this.deselectedColor;
    }

    /**
     * Getter for the TokenEnum corresponding to the Token object which has the selected color
     * that is the same as the Color of token enum in TokenEnum
     * @return Corresponding TokenEnum
     * @author Yida Chen
     */
    public TokenEnum getTokenEnum() {
        for (TokenEnum tokenEnum : TokenEnum.values()) {
            if (tokenEnum.getColor().equals(this.selectedColor)) { return tokenEnum; }
        }
        return null;
    }

}
