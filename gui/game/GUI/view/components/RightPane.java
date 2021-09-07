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
 * this class create a VBox as the right pane with some property
 * @author Jacky Lin
 */
public class RightPane {
    /** the VBox container*/
    private final VBox rightPane;

    /**
     * the constructor of the pane
     * create some properties of the pane
     * @author Jacky Lin
     */
    public RightPane() {
        //set right pane
        rightPane = new VBox(20);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setBorder( new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                new CornerRadii(10), BorderWidths.DEFAULT, new Insets(5))));
        rightPane.setPadding(new Insets(10));
        // set label
        Label pegsLabel = new Label("RESULTS");
        pegsLabel.setAlignment(Pos.CENTER);
        rightPane.getChildren().add(pegsLabel);
    }

    /**
     * Getter method for the HBox container
     * @return the VBox container
     */
    public VBox getRightPane() { return rightPane; }

    /**
     * this method add object to the container
     * @param elements add the objects to the container
     */
    public void add(Node... elements) {
        rightPane.getChildren().addAll(elements);
    }

    /**
     * this method add object to the container
     * @param c add the objects to the container
     */
    public void add(Collection<? extends Node> c) {
        rightPane.getChildren().addAll(c);
    }
}
