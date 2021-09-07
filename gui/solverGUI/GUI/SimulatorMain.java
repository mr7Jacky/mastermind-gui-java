package hw02.solverGUI.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Main class of the Simulator. Initiate the Model, view, and controller object of the
 * Simulator and start the GUI of the simulator.
 * @author Jacky Lin, Yida Chen
 */
public class SimulatorMain extends Application {
    /** The visual view of the simulator */
    private SimulatorView theView;

    /** The model of the simulator */
    private SimulatorModel theModel;

    /** The controller of the simulator */
    private SimulatorController theController;

    /** The stage of the simulator */
    public static Stage stage = new Stage();

    /**
     * Constructor of the SimulatorMain class
     * @throws Exception Exception during initialization
     * @author Jacky Lin
     */
    public SimulatorMain() throws Exception {
        init();
    }

    /**
     * Initiate the model, view, and controller of the Simulator
     * @throws Exception Exceptions during the initialization
     * @author Jacky Lin
     */
    @Override
    public void init() throws Exception {
        super.init();
        this.theModel = new SimulatorModel();
        this.theView = new SimulatorView(this.theModel);
        this.theController = new SimulatorController(this.theModel, this.theView);
    }

    /**
     * Start the GUI of the simulator
     * @param primaryStage Primary stage of the Simulator
     * @author Jacky Lin
     */
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        Scene scene = new Scene(this.theView.getRoot());
        //set the stage
        stage.setTitle("Mastermind");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
