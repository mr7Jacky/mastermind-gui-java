/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 10/31/19
 * Time: 11:15 PM
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
 * this class create a VBox as the center pane with some property
 * @author Jacky Lin
 */
public class CenterPane {
    /** the VBox container*/
    private final VBox centerPane;

    /**
     * the constructor of the pane
     * create some properties of the pane
     * @author Jacky Lin
     */

    public CenterPane() {
        //set center pane
        centerPane = new VBox(20);
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setBorder( new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                new CornerRadii(10), BorderWidths.DEFAULT, new Insets(5))));
        centerPane.setPadding(new Insets(10));
        // set label
        Label guess = new Label("YOUR GUESS");
        guess.setAlignment(Pos.CENTER);
        centerPane.getChildren().add(guess);
    }

    /**
     * Getter method for the HBox container
     * @return the HBox container
     */
    public VBox getCenterPane() { return centerPane; }

    /**
     * this method add object to the container
     * @param elements add the objects to the container
     */
    public void add(Node... elements) {
        centerPane.getChildren().addAll(elements);
    }

    /**
     * this method add object to the container
     * @param c add the objects to the container
     */
    public void add(Collection<? extends Node> c) {
        centerPane.getChildren().addAll(c);
    }
}
