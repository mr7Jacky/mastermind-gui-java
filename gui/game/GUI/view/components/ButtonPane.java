/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 10/31/19
 * Time: 11:05 PM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package hw02.game.GUI.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Collection;

/**
 * this class create a HBox as the button pane with some property
 * @author Jacky Lin
 */
public class ButtonPane {
    /** the HBox object as container*/
    private final HBox buttonPane;

    /**
     * the constructor of the pane
     * create some properties of the pane
     * @author Jacky Lin
     */
    public ButtonPane() {
        //set basic appearance of button
        buttonPane = new HBox(10);
        buttonPane.setPadding(new Insets(10));
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setBorder( new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                new CornerRadii(10), BorderWidths.DEFAULT,new Insets(5))));
        Label currentSelectLabel = new Label("Current Selection");
        add(currentSelectLabel);
    }

    /**
     * Getter method for the HBox container
     * @return the HBox container
     */
    public HBox getButtonPane() { return buttonPane; }

    /**
     * this method add object to the container
     * @param elements add the objects to the container
     */
    public void add(Node... elements) { buttonPane.getChildren().addAll(elements); }

    /**
     * this method add object to the container
     * @param c add the objects to the container
     */
    public void add(Collection<? extends Node> c) { buttonPane.getChildren().addAll(c); }

}
