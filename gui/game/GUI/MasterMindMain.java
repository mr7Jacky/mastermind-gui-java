package hw02.game.GUI;

import hw02.game.GUI.controller.MasterMindController;
import hw02.game.GUI.model.MasterMindModel;
import hw02.game.GUI.view.MasterMindView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * the main class to run the mastermind game, in relation to the
 * {@link MasterMindModel}, {@link MasterMindController}, {@link MasterMindView}
 * @author Yida Chen
 */

/**
 * the launcher of the master mind game
 * @author Jacky Lin, Yida Chen
 */
public class MasterMindMain extends Application {
    /** the model to for the app*/
    private MasterMindModel theModel;
    /** the layout for the app*/
    private MasterMindView theView;
    /** the controller for the app*/
    private MasterMindController theController;
    /** the stage to setup*/
    public static Stage stage = new Stage();

    /**
     * initialize the mastermind game
     * @throws Exception
     * @author Jacky Lin
     */
    public MasterMindMain() throws Exception {
        init();
    }

    /**
     * initialize all the object used in the game before started
     * @throws Exception the exception to throw
     * @author Yida Chen
     */
    @Override
    public void init() throws Exception {
        super.init();
        this.theModel = new MasterMindModel();
        this.theView = new MasterMindView(theModel);
        this.theController = new MasterMindController(theModel, theView);
    }

    /**
     * the start method to launch the game
     * @param primaryStage the primary stage to set and show
     * @author Yida Chen
     */
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        Scene scene = new Scene(theView.getRoot());
        //set the stage
        stage.setTitle("Mastermind");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
