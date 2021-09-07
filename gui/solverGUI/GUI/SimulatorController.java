/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 10/30/19
 * Time: 9:07 AM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package hw02.solverGUI.GUI;

import hw02.solverGUI.solver.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.util.Duration;

/**
 * This class is the controller for the simulation mode,
 * it allows user to select from 3 different solving mode for mastermind
 * including {@link MinimaxSolver}, {@link CustomSolver}, {@link RandomSolver}
 * it will generate the x and y axis accordingly with the animation effect.
 * @see <a href="https://docs.oracle.com/javafx/2/charts/bar-chart.htm">
 *     https://docs.oracle.com/javafx/2/charts/bar-chart.htm</a>
 *     <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html">
 *         https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html</a>
 * @author Jacky Lin, Yida Chen
 */
public class SimulatorController {
    /** Convert second to microsecond */
    public static final int SECOND_TO_MICRO = 1000000;
    /** the view of the simulation mode*/
    private SimulatorView theView;
    /** the model of the simulation mode*/
    private SimulatorModel theModel;
    private Timeline barChartTimeLine;
    private int numOfTries;

    /**
     * Constructor of the SimulatorController class
     * @param theModel Model of the Simulator
     * @param theView View of the Simulator
     * @author Jacky Lin
     */
    public SimulatorController(SimulatorModel theModel, SimulatorView theView) {
        this.theModel = theModel;
        this.theView = theView;

        setActionsOnStopButton();
        setActionsOnBegin();
        setActionsOnExit();
        this.theView.getUserInput().setOnAction(theView.getBegin().getOnAction());
    }

    /**
     * Set actions on the Begin simulation button
     * @author Yida Chen, Jacky Lin
     */
    private void setActionsOnBegin() {
        this.theView.getBegin().setOnAction(event -> {

            if (theView.getStatusIndicator().getText().equals("Please Make Selections")) {
                executePlot();
            }
            else if (theView.getStatusIndicator().getText().equals("Done!")){
                resetGame();
                executePlot();
            }
        });
    }

    /**
     * this method execute the plot process,
     * it will plot the bar chart of different solver accordingly
     * @author Jacky Lin, Yida Chen
     */
    private void executePlot() {
        numOfTries = getNumOfTries();
        theView.getStatisticSteps().setTitle(theView.getModeSelect().getValue() + " Steps Statistic");
        if (numOfTries != 0) {
            theView.getStatusIndicator().setText("Processing......");
            if (theView.getModeSelect().getSelectionModel().isSelected(1)) {
                startSolving(new MinimaxSolver(), 1, 1, 18,
                        5000,  60000, 1000);
            } else if (theView.getModeSelect().getSelectionModel().isSelected(2)) {
                startSolving(new RandomSolver(),  1000, 0, 8000,
                        100,  1000, 200);
            } else if (theView.getModeSelect().getSelectionModel().isSelected(3)) {
                startSolving(new CustomSolver(),  5, 0, 30,
                        100,  1000, 400);
            } else {
                Alert modeAlert = new Alert(Alert.AlertType.ERROR);
                modeAlert.setTitle("WRONG MODE SELECTION!");
                modeAlert.setHeaderText("Unspecified Simulation Mode");
                modeAlert.setContentText("Please select the specific solver mode");
                modeAlert.show();
                theView.getStatusIndicator().setText("Please Make Selections");
            }
        }
    }

    /**
     * set the action of the stop button
     * @author Yida Chen
     */
    private void setActionsOnStopButton(){
        theView.getStop().setOnAction(actionEvent -> {
            try {
                barChartTimeLine.stop();
                theView.getStatusIndicator().setText("Done!");
            } catch (Exception e) {
                return;
            }
        });
    }

    /**
     * this method reset the game and clear the data
     * @author Yida Chen, Jacky Lin
     */
    private void resetGame() {
        try{ barChartTimeLine.stop(); }
        catch (Exception e){ System.out.println("ERROR"); }
        Node temp = theView.getRoot().getChildren().get(1);
        theView.getRoot().getChildren().clear();
        theView.initTopPane();
        theView.getRoot().getChildren().add(temp);
        theView.initChartsPane();
        theView.initBottom();
        setActionsOnBegin();
        setActionsOnStopButton();
        setActionsOnExit();
    }

    /**
     * Get the number of simulations from the user.
     * @see <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html">
     *     https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html</a>
     * @return Number of simulations of the game
     * @author Yida Chen
     */
    private int getNumOfTries() {
        int numOfTries = 0;
        try {
            String tries = theView.getUserInput().getText();
            numOfTries = theModel.getNumOfTries(tries);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Solver Error");
            alert.setHeaderText("Incorrect input");
            alert.setContentText(String.format("Can not simulate by \"%s\" times!",
                    theView.getUserInput().getText()));
            alert.show();
        }
        return numOfTries;
    }

    /**
     * this method set the action on the exit button
     * @author Jacky Lin
     */
    private void setActionsOnExit() {
        theView.getExit().setOnAction(actionEvent -> { SimulatorMain.stage.close(); });
    }

    /**
     * Initiate the X axis of the bar chart.
     * @see <a href="https://docs.oracle.com/javafx/2/charts/bar-chart.htm">
     *     https://docs.oracle.com/javafx/2/charts/bar-chart.htm</a>
     * @param stepWidth Width of the  bins
     * @param stepLowerBound Lower bound of the bar chart
     * @param stepUpperBound Upper bound of the bar chart
     * @author Yida Chen
     */
    private void initiateXAxis(int stepWidth, int stepLowerBound, int stepUpperBound, int timeWidth, int timeUpperBound){
        int timeLowerBound = 0;
        for (int i = stepLowerBound; i < stepUpperBound; i+= stepWidth){
            String stepCategory;
            if (stepWidth == 1){ stepCategory = "" + i; }
            else { stepCategory = theModel.getCategory(stepWidth, i); }
            theView.getXyAxisSteps().getData().add(new XYChart.Data<>(stepCategory, 0));
        }
        if (stepWidth == 1){
            theView.getXyAxisSteps().getData().add(new XYChart.Data<>("" + stepUpperBound,0));
        }
        String stepCategory = "> " + stepUpperBound;
        theView.getXyAxisSteps().getData().add(new XYChart.Data<>(stepCategory,0));
        for (int i = timeLowerBound; i < timeUpperBound; i += timeWidth) {
            String timeCategory = theModel.getCategory(timeWidth, i);
            theView.getXyAxisTime().getData().add(new XYChart.Data<>(timeCategory, 0));
        }
        String timeCategory = "> " + timeUpperBound;
        theView.getXyAxisTime().getData().add(new XYChart.Data<>(timeCategory,0));
    }

    /**
     * Run the minimax solver mode of the game
     * @see <a href="https://docs.oracle.com/javafx/2/charts/bar-chart.htm">
     *     https://docs.oracle.com/javafx/2/charts/bar-chart.htm</a>
     * @param solver the solver mode to use
     * @param stepWidth Width of the bin
     * @param stepLowerBound Lower bound of the bar chart
     * @param stepUpperBound Upper bound of the bar chart
     * @author Yida Chen, Jacky Lin
     */
    private void startSolving(Solver solver, int stepWidth, int stepLowerBound,
                              int stepUpperBound, int timeWidth, int timeUpperBound, int duration) {
        initiateXAxis(stepWidth,stepLowerBound, stepUpperBound, timeWidth, timeUpperBound);
        barChartTimeLine = new Timeline();
        barChartTimeLine.getKeyFrames().add(new KeyFrame((Duration.millis(duration)), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Solver selectedSolver = solver;
                double[] data = selectedSolver.solverSimulation(new SolverSimulator());
                double step = data[1];
                for (XYChart.Series<String, Number> curData : theView.getStatisticSteps().getData()) {
                    int bin = theModel.getBinNum(step, stepUpperBound, stepWidth);
                    curData.getData().get(bin).setYValue(curData.getData().get(bin).getYValue().intValue() + 1);
                }
                double time = data[0] * SECOND_TO_MICRO;
                for (XYChart.Series<String, Number> curData: theView.getStatisticsTime().getData()) {
                    int bin = theModel.getBinNum(time, timeUpperBound, timeWidth);
                    curData.getData().get(bin).setYValue(curData.getData().get(bin).getYValue().intValue() + 1);
                }
            }
        }));
        barChartTimeLine.setCycleCount(numOfTries);
        barChartTimeLine.setAutoReverse(false);
        barChartTimeLine.play();
        barChartTimeLine.setOnFinished(event -> { theView.getStatusIndicator().setText("Done!"); });
    }

}
