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

package hw02.game.GUI.controller;

import hw02.game.*;
import hw02.game.GUI.MasterMindMain;
import hw02.game.GUI.model.MasterMindModel;
import hw02.game.GUI.view.MasterMindSecondStageView;
import hw02.game.GUI.view.MasterMindView;
import hw02.game.chess.Peg;
import hw02.game.chess.PegEnum;
import hw02.game.chess.Token;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Optional;

/**
 * The controller class for the game, it help add the actions to the node in view
 * User can select their token guess by clicking the circles. You need to click check button
 * after you are sure about your answers. Clear can clear the current selection
 * In the Config menu, you can drag the icon to adjust the difficulty be changing the pegs to guess
 * and number of times you can try.
 * @author Jacky Lin, Yida Chen
 * @see <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html">
 *     https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html</a>
 */
public class MasterMindController {
    /** the model for the game*/
    private MasterMindModel theModel;
    /** the view of the game*/
    private MasterMindView theView;
    /** the machine for the game*/
    private Machine m;
    /** the current steps*/
    private int currentStep;
    /** the total turns to guess*/
    private int totalTurns;

    /**
     * the constructor of the controller initialize the numbers and set action to the node
     * @param theModel the model of the game
     * @param theView the appearance of the game
     */
    public MasterMindController(MasterMindModel theModel, MasterMindView theView) {
        this.theModel = theModel;
        this.theView = theView;
        // set the machine
        this.m = new Machine(theModel.getNumOfPegs());
        m.generateTokens();
        // set tokens
        setTokensAction();
        // the button
        setCheckButton();
        setClearButton();
        //set option menu
        setOptionsActions();
        //initialize the numbers
        currentStep = 0;
        totalTurns = theModel.getNumOfTurns();
    }

    /**
     * This method setup the option menu, including the config and exit button
     * @author Jacky Lin
     */
    private void setOptionsActions() {
        setExit();
        setConfig();
    }

    /**
     * The config button will open a new stage that allow users to select between 5-15 turns and 3-8 pegs
     * @author Jacky Lin
     */
    private void setConfig() {
        this.theView.getConfig().setOnAction(event -> {
            MasterMindSecondStageView mmss = new MasterMindSecondStageView(this.theModel);
            mmss.getStage().show();
            mmss.getApply().setOnAction(event1 -> {
                this.theModel.setNumOfTurns((int)mmss.getTurnsInput().getValue());
                this.theModel.setNumOfPegs((int)mmss.getPegsInput().getValue());
                // Reset Machine
                newGame();
                mmss.getStage().close();
            });
        });
    }

    /**
     * This method setup the exit button, when press this button the game will end
     * @author Jacky Lin
     */
    private void setExit() {
        this.theView.getExit().setOnAction(event -> {
            MasterMindMain.stage.close();
        });
    }

    /**
     * this method will reset he game with all the new machine. the model and the view
     * it will also reset the steps and turns
     * @author Yida Chen
     */
    private void newGame() {
        // Reset Machine
        this.m = new Machine(theModel.getNumOfPegs());
        this.m.generateTokens();
        // Reset histories
        this.theModel.setCurrentGuess();
        this.theModel.getHistoryTokenGuesses().clear();
        this.theModel.getHistoryPegResult().clear();
        // Reset interface
        this.theView.setCenterPane();
        this.theView.setRightPane();
        this.theView.setBottom();

        this.resetCurrentSteps();
        this.setTotalTurns(this.theModel.getNumOfTurns());
    }

    /**
     * this method help reset the current steps
     * @author Yida Chen
     */
    private void resetCurrentSteps(){
        this.currentStep = 0;
    }

    /**
     * this method set the total turns of the game
     * @param totalTurns the total turns of the game
     * @author Yida Chen
     */
    private void setTotalTurns(int totalTurns) {
        this.totalTurns = totalTurns;
    }



    /**
     * This button setup the clear button
     * When it is pressed the current selection will be clear to empty
     * You can only clear the current selection without change the history
     * @author Jacky Lin
     */
    private void setClearButton() {
        this.theView.getClear().setOnAction(event -> {
            theModel.resetCurrentGuess();
            bindingCurrentGuessColor();
        });
    }

    /**
     * this method setup the check button, which require the machine to check the input
     * update the history of guess and result and clear the current guess data.
     * @author Jacky Lin, Yida Chen
     */
    private void setCheckButton() {
        this.theView.getCheck().setOnAction(event -> {
            if (theModel.validGuess()){
                for (Token t: this.theModel.getCurrentGuess()) {
                    this.theModel.getHistoryTokenGuesses().add(new Token(t.getSelectedColor()));
                }
                ArrayList<Peg> resultPeg = new ArrayList<>();
                resultPeg = m.tokenChecker(this.theModel.getCurrentGuess());
                this.theModel.setResultPeg(resultPeg);
                //update the history data
                updateGuessToHistory();
                updatePegToHistory();
                //reset the current guess
                theModel.resetCurrentGuess();
                currentStep++;
                // end of the game
                if (theModel.guessOutCode(resultPeg)){ endOfGameResult(true); }
                else if (currentStep == totalTurns){ endOfGameResult(false); }
            }
        });
    }


    /**
     * This method will show the user the result after finished the game and ask if the user want to play again
     * @see <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html">
     *     https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html</a>
     * @param win the boolean indicate whether the user win or not
     * @author Yida Chen
     */
    private void endOfGameResult(boolean win){
        //setup the alert container
        Alert result = new Alert(Alert.AlertType.CONFIRMATION);
        result.setTitle("End Result");
        result.setHeaderText("Correct Answers: ");
        // add the correct answers to the container
        HBox correctAnswers = new HBox(10);
        correctAnswers.setPadding(new Insets(5));
        ArrayList<Circle> answers = theModel.getCorrectAnswers(m.getTokenEnums());
        correctAnswers.getChildren().addAll(answers);
        result.setGraphic(correctAnswers);
        // the the conformation text to the model
        theModel.setConfirmationText(win, result);
        // play again button for the game result
        ButtonType playAgain = new ButtonType("Play again");
        ButtonType exit = new ButtonType("Exit");
        result.getButtonTypes().setAll(exit, playAgain);
        Optional<ButtonType> feedback = result.showAndWait();
        // run different action according to users selections
        if (feedback.get() == playAgain) { newGame(); }
        else { MasterMindMain.stage.close();}
    }




    /**
     * this method update the result history of pegs
     * @author Jacky Lin,Yida Chen
     */
    private void updatePegToHistory() {
        for (int i = 0; i < this.theModel.getHistoryPegResult().size(); i++) {
            this.theView.getGuessResultHistory().get(i).setFill(
                    this.theModel.getHistoryPegResult().get(i).getCurrentColor());
            // If it is a good token peg, set a border for the peg.
            if (this.theModel.getHistoryPegResult().get(i).getCurrentColor().equals(PegEnum.GOODCOLOR.getColor())){
                this.theView.getGuessResultHistory().get(i).setStroke(Color.DARKGRAY);
            }
        }
    }

    /**
     * this method update the result history of guesses
     * @author Jacky Lin,Yida Chen
     */
    private void updateGuessToHistory() {
        for (int i = 0; i < this.theModel.getHistoryTokenGuesses().size(); i++) {
            this.theView.getUserGuessesHistory().get(i).setFill(
                    this.theModel.getHistoryTokenGuesses().get(i).getSelectedColor());
        }
    }

    /**
     * this method set the action to all the choices tokens in the left pane
     * your choices will indicate in the bottom pane in the view
     * @author Jacky Lin
     */
    private void setTokensAction() {
        for (Circle c : this.theView.getTokensChoices()) {
            c.setOnMouseClicked(event -> {
                for (Token t : this.theModel.getCurrentGuess()) {
                    if (t.getSelectedColor().equals(Color.GRAY)) {
                        t.setSelectedColor(c.getFill());
                        t.toggleToken();
                        break;
                    }
                }
                bindingCurrentGuessColor();
            });
        }
    }

    /**
     * Binding the circle color in the view with the color in the model
     * @author Jacky Lin
     */
    private void bindingCurrentGuessColor() {
        for (Circle circle : this.theView.getCurrentGuess()) {
            Token token = this.theModel.getCurrentGuess().get(this.theView.getCurrentGuess().indexOf(circle));
            circle.fillProperty().bind(Bindings.when(token.isSelectedProperty()).then(token.getSelectedColor())
                    .otherwise(token.getDeselectedColor()));
        }
    }

}
