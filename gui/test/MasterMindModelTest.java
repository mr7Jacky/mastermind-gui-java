package hw02.test;

import hw02.game.GUI.model.MasterMindModel;
import hw02.game.chess.Token;
import hw02.game.chess.TokenEnum;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test class for the MasterMindModel class
 * Test the validGuess and resetCurrentGuess methods of the MasterMindModel
 * class.
 * @author Yida Chen
 */
class MasterMindModelTest {
    /** Test MasterMindModel object */
    private MasterMindModel theModel;

    /**
     * Set up the model before each test
     * @author Yida Chen
     */
    @BeforeEach
    void setUp() {
        theModel = new MasterMindModel();
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Test is the guess from the user is valid to check against the code.
     * E.g. A valid guess is a guess with all four tokens has been selected by the user from the six possible choices.
     * A guess with RED RED BLUE GRAY is not valid since the GRAY is the default color of the token which is not in the
     * six possible choice
     * @author Yida Chen
     */
    @Test
    void validGuess() {
        // User did not give his guess yet, so the guess is currently invalid.
        assertFalse(theModel.validGuess());

        // User choose four tokens for his guess
        for (int i = 0; i < theModel.getCurrentGuess().size(); i++){
            theModel.getCurrentGuess().get(i).setSelectedColor(TokenEnum.values()[i].getColor());
        }

        // All tokens have been selected. Guess is valid.
        assertTrue(theModel.validGuess());
    }

    /**
     * Test the resetCurrentGuess method
     * The token will be reset to GRAY color.
     * @author Yida Chen
     */
    @Test
    void resetCurrentGuess() {
        // User choose four tokens for his guess
        for (int i = 0; i < theModel.getCurrentGuess().size(); i++){
            theModel.getCurrentGuess().get(i).setSelectedColor(TokenEnum.values()[i].getColor());
        }

        // After the reset the colorS of four tokens should all be GRAY
        ArrayList<Token> guessAfterResrt= new ArrayList<>();
        for (int i = 0; i < theModel.getCurrentGuess().size(); i++){
            guessAfterResrt.add(new Token(Color.GRAY));
        }

        // Before reset the colors of four tokens are not GRAY.
        for (int i = 0; i < theModel.getCurrentGuess().size(); i++){
            assertNotEquals(guessAfterResrt.get(i).getSelectedColor(), theModel.getCurrentGuess().get(i).getSelectedColor());
        }

        // After reset the colors of four tokens are GRAY.
        theModel.resetCurrentGuess();
        for (int i = 0; i < theModel.getCurrentGuess().size(); i++){
            assertEquals(guessAfterResrt.get(i).getSelectedColor(), theModel.getCurrentGuess().get(i).getSelectedColor());
        }
    }
}