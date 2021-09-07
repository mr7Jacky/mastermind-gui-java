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

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.util.Collection;
/**
 * this class create a FlowPane as the top pane with some property
 * @author Jacky Lin
 */
public class TopPane {
    /** the flow pane container*/
    private final FlowPane topPane;

    /**
     * the constructor of the pane
     * create some properties of the pane
     * @author Jacky Lin
     */
    public TopPane() {
        // setup flow pane
        topPane = new FlowPane();
        topPane.setAlignment(Pos.CENTER_LEFT);
        topPane.setPrefWidth(350);
        topPane.setPrefHeight(40);
    }

    /**
     * Getter method for the HBox container
     * @return the FlowPane container
     */
    public FlowPane getTopPane() { return topPane; }

    /**
     * this method add object to the container
     * @param elements add the objects to the container
     */
    public void add(Node... elements) { topPane.getChildren().addAll(elements); }

    /**
     * this method add object to the container
     * @param c add the objects to the container
     */
    public void add(Collection<? extends Node> c) {
        topPane.getChildren().addAll(c);
    }
}
