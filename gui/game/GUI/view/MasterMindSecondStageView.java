/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 10/28/19
 * Time: 5:23 PM
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class help setup the secondary stage of the configuration interface
 * It allows the user to change their pegs and turns for game, as they type in the integer they want to play with.
 * It will indicate current peg and turns number
 * After user finish input their number of games, they can click "Apply Changes" Button {@link Button}
 * @author Jacky Lin
 */
public class MasterMindSecondStageView {
    /** the second stage to show*/
    private Stage stage;
    /** root node is a VBox*/
    private VBox root;
    /** the text field to input turns*/
    private Slider turnsInput;
    /** the text field to input pegs*/
    private Slider pegsInput;
    /** the button to apply changes*/
    private Button apply;

    /**
     * Constructor of the MasterMindSecondStageView
     * Initialize all the variable but it doesn't show.
     * @param theModel the model of the mastermind app
     * @author Jacky Lin
     */
    public MasterMindSecondStageView(MasterMindModel theModel) {
        initRoot();
        initSlider(theModel);
        // create two labels
        Label turnsLabel = new Label("Input the turns you want to guess for game.");
        Label pegsLabel = new Label("Input the pegs you want to have per turn.");

        root.getChildren().addAll(turnsLabel,turnsInput,pegsLabel,pegsInput,apply);
        initStage();
    }

    /**
     * this method initialize the text field and button
     * @param theModel the model of the mastermind app using for get the current data
     * @author Jacky Lin
     */
    private void initSlider(MasterMindModel theModel) {
        //initialize the turns slider
        initTurnInput();
        //initialize the pegs slider
        initPegInput();
        //initialize the button
        apply = new Button("Apply Changes");
    }

    /**
     * this method initialize the slider allow user to select the pegs number
     * @author Jacky Lin
     */
    private void initPegInput() {
        pegsInput = new Slider(3,8,0);
        pegsInput.setShowTickLabels(true);
        pegsInput.setShowTickMarks(true);
        pegsInput.setSnapToTicks(true);
        pegsInput.setMajorTickUnit(1);
        pegsInput.setMinorTickCount(0);
    }

    /**
     * this method initialize the slider allow user to select the turns number
     * @author Jacky Lin
     */
    private void initTurnInput() {
        turnsInput = new Slider(5,15,0);
        turnsInput.setShowTickLabels(true);
        turnsInput.setShowTickMarks(true);
        turnsInput.setSnapToTicks(true);
        turnsInput.setMajorTickUnit(1);
        turnsInput.setMinorTickCount(0);
    }

    /**
     * This method initialize the root node with CENTER Alignment and 10 spacing
     * @author Jacky Lin
     */
    private void initRoot() {
        root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
    }

    /**
     * this method initialize the stage, add title and root to the stage
     * @author Jacky Lin
     */
    private void initStage() {
        stage = new Stage();
        stage.setTitle("Configuration");
        stage.setScene(new Scene(root));
        stage.sizeToScene();
    }

    /**
     * Getter method for the stage
     * @return the secondary stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Getter method for the input text field of the turns
     * @return the text field
     */
    public Slider getTurnsInput() {
        return turnsInput;
    }

    /**
     * Getter method for the input text field of the pegs
     * @return the text field
     */
    public Slider getPegsInput() {
        return pegsInput;
    }

    /**
     * Getter method for the apply button
     * @return the button to save the change
     */
    public Button getApply() {
        return apply;
    }

}
