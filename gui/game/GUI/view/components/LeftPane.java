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
import javafx.scene.layout.VBox;

import java.util.Collection;
/**
 * this class create a VBox as the left pane with some property
 * @author Jacky Lin
 */
public class LeftPane {
    /** the VBox container*/
    private final VBox leftPane;

    /**
     * the constructor of the pane
     * create some properties of the pane
     * @author Jacky Lin
     */
    public LeftPane() {
        //set left pane
        leftPane = new VBox(10);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(10));
    }

    /**
     * Getter method for the HBox container
     * @return the VBox container
     */
    public VBox getLeftPane() { return leftPane; }

    /**
     * this method add object to the container
     * @param elements add the objects to the container
     */
    public void add(Node... elements) {
        leftPane.getChildren().addAll(elements);
    }

    /**
     * this method add object to the container
     * @param c add the objects to the container
     */
    public void add(Collection<? extends Node> c) {
        leftPane.getChildren().addAll(c);
    }
}
