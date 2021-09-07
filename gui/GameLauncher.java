package hw02;

import hw02.game.GUI.MasterMindMain;
import hw02.solverGUI.GUI.SimulatorMain;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * This class build the main stage and launcher of different mode
 * {@link MasterMindMain} and {@link SimulatorMain}
 * @author Jacky Lin
 */
public class GameLauncher extends Application {
    /** the root node of the launcher*/
    private VBox root;
    /** the play mode launcher trigger*/
    private Label playMode;
    /** the simulate mode trigger*/
    private Label simulateMode;
    /** the exit the game label*/
    private Label exit;

    /**
     * main method to launch the game
     * @param args args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * the start method for the app set up the stage
     * @param primaryStage the primary stage to setup
     * @author Jacky Lin
     */
    @Override
    public void start(Stage primaryStage) {
        root = new VBox(10);
        root.setPadding(new Insets(30));
        initLabel();
        //set up scene
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setTitle("Mastermind");
        primaryStage.show();
    }

    /**
     * initialize the simulate label actions
     * @author Jacky Lin
     */
    private void initSimulateModeController() {
        simulateMode.setOnMouseClicked(event -> {
            SimulatorMain sm = null;
            try { sm = new SimulatorMain(); }
            catch (Exception e) { e.printStackTrace(); }
            sm.start(SimulatorMain.stage);
        });
        simulateMode.setOnMouseEntered(event -> { simulateMode.setGraphic(new Circle(5)); });
        simulateMode.setOnMouseExited(event -> { simulateMode.setGraphic(null); });
    }

    /**
     * initialize the label
     * @author Jacky Lin
     */
    private void initLabel() {
        initPlayMode();
        initSimulateMode();
        initExit();
        root.getChildren().addAll(playMode, simulateMode,exit);
    }

    /**
     * initialize the exit label
     * @author Jacky Lin
     */
    private void initExit() {
        initExitView();
        initExitController();
    }

    /**
     * add the function to the exit label, it will exit system after clicked
     * it will have the circle appear to indicate you are selected
     * @author Jacky Lin
     */
    private void initExitController() {
        exit.setOnMouseClicked(event -> { System.exit(1); });
        exit.setOnMouseExited(event -> { exit.setGraphic(null); });
        exit.setOnMouseEntered(event -> { exit.setGraphic(new Circle(5)); });
    }

    /**
     * the appearance of the exit view
     * @author Jacky Lin
     */
    private void initExitView() {
        exit = new Label("Exit Game");
        exit.setPrefSize(200, 100);
        exit.setFont(Font.font(null, FontWeight.BOLD, 30));
        exit.setTextFill(Color.RED);
        exit.setEffect(getDropShadow());
    }

    /**
     * initialize the simulate mode
     * @author Jacky Lin
     */
    private void initSimulateMode() {
        initSimulateModeView();
        initSimulateModeController();
    }

    /**
     * initialize the simulated mode label appearance
     * @author Jacky Lin
     */
    private void initSimulateModeView() {
        simulateMode = new Label("SIMULATE");
        simulateMode.setPrefSize(200, 100);
        simulateMode.setFont(Font.font(null, FontWeight.BOLD, 30));
        simulateMode.setTextFill(Color.ORANGE);
        simulateMode.setEffect(getDropShadow());
    }

    /**
     * setup the drop shadow the drop shadow
     * @return the shadow effect
     * @author Jacky Lin
     */
    private DropShadow getDropShadow() {
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(10);
        shadow.setOffsetY(10);
        shadow.setHeight(10);
        shadow.setHeight(10);
        shadow.setBlurType(BlurType.THREE_PASS_BOX);
        shadow.setColor(Color.GRAY);
        return shadow;
    }

    /**
     * initialize the play mode label
     * @author Jacky Lin
     */
    private void initPlayMode() {
        initPlayModeView();
        initPlayModeController();
    }

    /**
     * add the action to the play mode label
     * @author Jacky Lin
     */
    private void initPlayModeController() {
        playMode.setOnMouseClicked(event -> {
            MasterMindMain mm = null;
            try { mm = new MasterMindMain(); }
            catch (Exception e) { e.printStackTrace(); }
            mm.start(SimulatorMain.stage);
        });
        playMode.setOnMouseEntered(event -> { playMode.setGraphic(new Circle(5)); });
        playMode.setOnMouseExited(event -> { playMode.setGraphic(null); });
    }

    /**
     * initialize the play mode view
     * @author Jacky Lin
     */
    private void initPlayModeView() {
        playMode = new Label("PLAY");
        playMode.setPrefSize(200,100);
        playMode.setFont(Font.font(null, FontWeight.BOLD, 30));
        playMode.setTextFill(Color.BLUE);
        playMode.setEffect(getDropShadow());
    }
}
