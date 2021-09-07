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

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * SimulatorView class. The class contains the visual components of the Simulator
 * @see <a href="https://docs.oracle.com/javafx/2/charts/bar-chart.htm">
 *     https://docs.oracle.com/javafx/2/charts/bar-chart.htm</a>
 * @author Jacky Lin, Yida Chen
 */
public class SimulatorView {
    /** The model of the Simulator */
    private SimulatorModel theModel;
    /** The VBox root */
    private VBox root;
    /** Drop down box for the model of the solver*/
    private ComboBox<String> modeSelect;
    /** X Y Axis of the steps chart */
    private XYChart.Series xyAxisSteps;
    /** X Y Axis of the time chart */
    private XYChart.Series xyAxisTime;
    /** Bar chart for the solver used steps */
    private BarChart<String, Number> statisticSteps;
    /** Bar chart for the solver used cpu time */
    private BarChart<String, Number> statisticsTime;
    /** Text field contains the user input */
    private TextField userInput;
    /** Label that shows the status of the simulations */
    private Label statusIndicator;
    /** HBox represent the pane of charts*/
    private HBox chartsPane;
    /** Reset button */
    private Button stop;
    /** Exit button */
    private Button exit;
    /** Begin button */
    private Button begin;

    /**
     * Constructor of the SimulatorView class, initiate all the panes and graphic
     * components in them.
     * @param theModel SimulatorModel object represent the model of the simulator
     * @author Jacky Lin, Yida Chen
     */
    public SimulatorView(SimulatorModel theModel) {
        // Initiate the model
        this.theModel = theModel;

        // Initiate the root
        root = new VBox(10);

        // Initiate the rest
        initTopPane();
        initCenterPane();
        initChartsPane();
        initBottom();
    }

    /**
     * Initiate the top pane of the simulator with a HBox object
     * @author Jacky Lin
     */
    public void initTopPane() {
        // Basic config of the top pane
        HBox topPane = new HBox(10);
        topPane.setPadding(new Insets(15,0,0,0));
        topPane.setAlignment(Pos.CENTER);
        initLabel(topPane);
        root.getChildren().add(topPane);
    }

    /**
     * This method initialize the label and indicator that can indicate the current situation of the processing
     * @param topPane the container to add the label
     * @author Jacky Lin
     */
    private void initLabel(HBox topPane) {
        Label statusLabel = new Label("Current Status: ");
        // set the indicator
        statusIndicator = new Label("Please Make Selections");
        statusIndicator.setPadding(new Insets(10));
        statusIndicator.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                new CornerRadii(1), BorderWidths.DEFAULT)));
        // add to container
        topPane.getChildren().addAll(statusLabel,statusIndicator);
    }

    /**
     * Initiate the bar chart and the chart's X Y axis with used step statistics.
     * @see <a href="https://docs.oracle.com/javafx/2/charts/bar-chart.htm">
     *     https://docs.oracle.com/javafx/2/charts/bar-chart.htm</a>
     * @author Jacky Lin
     */
    public void initStepChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        statisticSteps = new BarChart<>(xAxis, yAxis);
        statisticSteps.setTitle("Statistics for Solving Steps");
        yAxis.setLabel("Frequency");
        xAxis.setLabel("Used Steps (Step)");
        xyAxisSteps = new XYChart.Series();
        statisticSteps.getData().add(xyAxisSteps);
        chartsPane.getChildren().add(statisticSteps);
    }

    /**
     * Initiate the bar chart and the chart`s X Y axis with used cpu time statistics.
     * @see <a href="https://docs.oracle.com/javafx/2/charts/bar-chart.htm">
     *     https://docs.oracle.com/javafx/2/charts/bar-chart.htm</a>
     * @author Yida Chen
     */
    public void initTimeChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        statisticsTime = new BarChart<>(xAxis, yAxis);
        statisticsTime.setTitle("Statistic for CPU time");
        yAxis.setLabel("Frequency");
        xAxis.setLabel("Time (microsecond)");
        xyAxisTime = new XYChart.Series();
        statisticsTime.getData().add(xyAxisTime);
        chartsPane.getChildren().add(statisticsTime);

    }

    /**
     * Initiate the pane contains all charts of the simulator
     * @author Jacky LIn
     */
    public void initChartsPane() {
        chartsPane = new HBox(10);
        initStepChart();
        initTimeChart();
        root.getChildren().add(chartsPane);
    }

    /**
     * Initiate the center pane of the simulator which contains the modeSelect drop down box
     * and userInput TextFiled that receives the number of simulations from the user.
     * @author Jacky Lin
     */
    public void initCenterPane() {
        HBox centerPane = new HBox(10);
        begin = new Button("Begin Solving!");
        modeSelect = new ComboBox<>();
        modeSelect.getItems().addAll("Select Mode","Mini Max Solver","Random Solver","Custom Solver");
        modeSelect.getSelectionModel().selectFirst();

        userInput = new TextField();
        userInput.setAlignment(Pos.CENTER);
        userInput.setPromptText("Input turns to simulate");

        stop = new Button("Stop Simulation");

        centerPane.getChildren().addAll(modeSelect,userInput,begin, stop);
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setPadding(new Insets(10));

        root.getChildren().add(centerPane);
    }

    /**
     * Initiate the Bottom pane of the simulator
     * @author Yida Chen
     */
    public void initBottom() {
        exit = new Button("Exit Simulations");
        HBox bottom = new HBox(10);
        bottom.setPadding(new Insets(5));
        bottom.getChildren().add(exit);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        root.getChildren().add(bottom);
    }

    /**
     * Getter method for the userInput TextField which receive the input from the user.
     * @return TextFiled object contains userInput
     */
    public TextField getUserInput() {
        return userInput;
    }

    /**
     * Getter method for the exit button
     * @return exit button of the view
     */
    public Button getExit() {
        return exit;
    }

    /**
     * Getter method for the reset button
     * @return reset button of the view
     */
    public Button getStop() {
        return stop;
    }

    /**
     * Getter method for the X Y axis of the time chart
     * @return XYChart.Series represent the X Y Axis of the time chart
     */
    public XYChart.Series getXyAxisTime() {
        return xyAxisTime;
    }

    /**
     * Getter method for the Bar chart of the used cpu time statistics
     * @return Bar chart contains the statistics of the used cpu time
     */
    public BarChart<String, Number> getStatisticsTime() {
        return statisticsTime;
    }

    /**
     * Getter method for VBox root
     * @return VBox object represent the root of the view
     */
    public VBox getRoot() {
        return root;
    }

    /**
     * Getter method for the modeSelect drop down box
     * @return modeSelect drop down box
     */
    public ComboBox<String> getModeSelect() {
        return modeSelect;
    }

    /**
     * Getter method method for the begin button
     * @return begin button
     */
    public Button getBegin() {
        return begin;
    }

    /**
     * Getter method of the X Y axis of the used step bar chart
     * @return XYChart.Series represent the X Y axis of the used step bar chart
     */
    public XYChart.Series getXyAxisSteps() {
        return xyAxisSteps;
    }

    /**
     * Getter method for the bar chart of the used step statistics
     * @return bar chart contains the steps data
     */
    public BarChart<String, Number> getStatisticSteps() {
        return statisticSteps;
    }

    /**
     * Getter method for the statusInidicator Label
     * @return statusIndicator Label which contains the current status of the simulation.
     */
    public Label getStatusIndicator() {
        return statusIndicator;
    }
}
