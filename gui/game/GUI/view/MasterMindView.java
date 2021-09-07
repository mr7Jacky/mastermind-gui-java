/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 10/26/19
 * Time: 2:59 PM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package hw02.game.GUI.view;

import hw02.game.GUI.model.MasterMindModel;
import hw02.game.GUI.view.components.*;
import hw02.game.chess.Token;
import hw02.game.chess.TokenEnum;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * this class create the view of the mastermind game interface
 * @author Jacky Lin, Yida Chen
 */
public class MasterMindView {
    /** the model of the mastermind*/
    private MasterMindModel theModel;
    /** the root node of*/
    private BorderPane root;
    /** a list of the token choice*/
    private ArrayList<Circle> tokensChoices;
    /** the menu container of the exit and config*/
    private MenuButton menu;
    /** the exit button*/
    private MenuItem exit;
    /** the config button*/
    private MenuItem config;
    /** the check button to check the inout*/
    private Button check;
    /** the clear button to clear the token*/
    private Button clear;
    /** a list of guess history*/
    private ArrayList<Circle> userGuessesHistory;
    /** a list of the result history*/
    private ArrayList<Circle> guessResultHistory;
    /** the circle of the current*/
    private ArrayList<Circle> currentGuess;

    /**
     * the constructor of the master mind view, setup all the panes
     * @author Jacky Lin, Yida Chen
     */
    public MasterMindView(MasterMindModel theModel) {
        // setup model
        this.theModel = theModel;

        // setup root
        root = new BorderPane();
        root.setPadding(new Insets(20));

        //set the rest
        setTopPane();
        setLeftPane();
        setCenterPane();
        setRightPane();
        setBottom();
    }

    /**
     * set the button pane in the root node
     * including the circle indicating the current selection
     * @author Jacky Lin
     */
    public void setBottom() {
        //button
        currentGuess = new ArrayList<>();
        for (Token t: theModel.getCurrentGuess()) {
            Circle c = new Circle(20);
            c.setFill(t.getDeselectedColor());
            currentGuess.add(c);
        }
        ButtonPane buttonPane = new ButtonPane();
        buttonPane.add(currentGuess);
        root.setBottom(buttonPane.getButtonPane());
    }

    /**
     * set the right pane including circles indicating the history of the result
     * @author Jacky Lin
     */
    public void setRightPane() {
        RightPane rightPane = new RightPane();
        guessResultHistory = new ArrayList<>();
        for (int i = 0; i < theModel.getNumOfTurns(); i++) {
            HBox tempBox = new HBox(10);
            for (int j = 0; j< theModel.getNumOfPegs(); j++) {
                Circle c = new Circle(10);
                c.setFill(Color.GRAY);
                tempBox.getChildren().add(c);
                guessResultHistory.add(c);
            }
            rightPane.add(tempBox);
        }
        root.setRight(rightPane.getRightPane());
    }

    /**
     * set the center pane including the circles, indicating the guesses history
     * @author Jacky Lin
     */
    public void setCenterPane() {
        CenterPane centerPane = new CenterPane();
        userGuessesHistory = new ArrayList<>();
        for (int i = 0; i < theModel.getNumOfTurns(); i++) {
            HBox tempBox = new HBox(10);
            tempBox.setAlignment(Pos.CENTER);
            for (int j = 0; j < theModel.getNumOfPegs(); j++) {
                Circle c = new Circle(10);
                c.setFill(Color.GRAY);
                userGuessesHistory.add(c);
                tempBox.getChildren().add(c);
            }
            centerPane.add(tempBox);
        }
        root.setCenter(centerPane.getCenterPane());
    }

    /**
     * set the left of pane including the clear and check button
     * there are six tokens with colors that allows users to select to guess the result
     * @author Jacky Lin
     */
    private void setLeftPane() {
        // set button
        check = new Button("Check");
        clear = new Button("Clear");
        // setup array list
        tokensChoices = new ArrayList<>();
        for (TokenEnum t : TokenEnum.values()) {
            Circle temp = new Circle(20);
            temp.setFill(t.getColor());
            tokensChoices.add(temp);
        }
        LeftPane leftPane = new LeftPane();
        //adding
        leftPane.add(check,clear);
        leftPane.add(tokensChoices);
        root.setLeft(leftPane.getLeftPane());
    }

    /**
     * set the top pane including the menu
     * @author Yida Chen
     */
    private void setTopPane() {
        TopPane topPane = new TopPane();
        // buttons setup
        config = new MenuItem("CONFIG");
        exit = new MenuItem("EXIT");
        menu = new MenuButton("Options", null, config, exit);
        // add button to flow pane
        topPane.add(menu);
        //set top pane
        root.setTop(topPane.getTopPane());
    }

    /**
     * the getter method for the root node
     * @return BorderPane for the stage
     */
    public BorderPane getRoot() { return root; }

    /**
     * the tokens user can choose in the game
     * @return an array list contains all the possible choices
     */
    public ArrayList<Circle> getTokensChoices() { return tokensChoices; }

    /**
     * Getter method for the history guess
     * @return a list of circle indication of history guess
     */
    public ArrayList<Circle> getUserGuessesHistory() { return userGuessesHistory; }

    /**
     * getter method for the check button
     * @return the button for check the result
     */
    public Button getCheck() { return check; }

    /**
     * the getter method for the clear button
     * @return the button to clear the current choice
     */
    public Button getClear() { return clear; }

    /**
     * Getter method for get the list of current choice
     * @return an array list of tokens indicating the current choices
     */
    public ArrayList<Circle> getCurrentGuess() { return currentGuess; }

    /**
     * Getter method for get the list of history result
     * @return an array list of tokens indicating the guess result history
     */
    public ArrayList<Circle> getGuessResultHistory() { return guessResultHistory; }

    /**
     * Getter method for the exit button
     * @return a menu item that can exit the game
     */
    public MenuItem getExit() { return exit; }

    /**
     * Getter method for configuration
     * @return a menu item that can change some basic setting
     */
    public MenuItem getConfig() { return config; }
}
