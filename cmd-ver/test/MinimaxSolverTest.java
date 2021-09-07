package hw01.test;

import hw01.game.Token;
import hw01.solver.MinimaxSolver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test class of the MinimaxSolver
 * Test the tokensToString and checkCode methods of the solver.
 * @author Yida Chen
 */
class MinimaxSolverTest {
    private MinimaxSolver solver;

    /**
     * Set up a minimaxSolver object solver before each test
     * @author Yida Chen
     */
    @BeforeEach
    void setUp() {
        solver = new MinimaxSolver();
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Test the tokensToString method of the solver
     * This method transfer the Token array representation fo the guess
     * to the String representation fo the guess.
     * @author Yida Chen
     */
    @Test
    void tokensToString() {
        // The token generate by generateGuessTokens are  BBGG (1122) the initial guess
        Token[] tokenGuess = solver.generateGuessTokens();

        // Expected initial guess of the minimax Solver
        String stringGuess = "1122";

        assertEquals(stringGuess,solver.tokensToString(tokenGuess));
    }

    /**
     * Test the checkCode method of the solver
     * This method return a String contained the response to the test guess
     * based on the checking code.
     * @author Yida Chen
     */
    @Test
    void checkCode() {
        // The code used for checking
        String code = "1124";

        // Test guess
        String guess = "4122";

        /** The tokens 1 2  in the guess match with the 1 2 in the code in the same spot
         *  So there are two ** peg in the response. The 4 in guess is match with the
         *  4 in the code but in the wrong place, and there is one + peg. The second 2 in the
         *  guess does not match anything in the code. Add one _ to the response
         */
        String expectedResponse = "**+_";

        assertEquals(expectedResponse, solver.checkCode(guess, code));
    }
}