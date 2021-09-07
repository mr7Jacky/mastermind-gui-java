package hw01.test;

import hw01.game.Machine;
import hw01.game.Token;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * test for class {@link Machine}
 * @author Jacky Lin
 */
class MachineTest {
    /** the Machine {@link hw01.game.Machine} objectfor test*/
    private Machine m;

    /**
     * this method is to initialize the machine object for the other test
     * @author Jacky Lin
     */
    @BeforeEach
    void setUp() {
        //create a new machine object
        m = new Machine();
        //instead of using the generate the class, we set the token list in the m deliberately
        //the right answer is 1136
        m.getTokens()[0] = Token.B;
        m.getTokens()[1] = Token.O;
        m.getTokens()[2] = Token.O;
        m.getTokens()[3] = Token.Y;
        // add data to the expectCounter
        m.getExpectCounter().addToken(Token.B);
        m.getExpectCounter().addToken(Token.O);
        m.getExpectCounter().addToken(Token.O);
        m.getExpectCounter().addToken(Token.Y);
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * test to getToken method
     * @author Jacky Lin
     */
    @Test
    void getTokens() {
        // add all the tokens a list
        Token[] testTokens = new Token[4];
        testTokens[0] = Token.B;
        testTokens[1] = Token.O;
        testTokens[2] = Token.O;
        testTokens[3] = Token.Y;
        // check the with the tokens list in the machine
        for (int i = 0; i < testTokens.length; i++) {
            assertEquals(testTokens[i].getNum(),m.getTokens()[i].getNum());
        }
    }

    /**
     * test method for printTokens
     * @author Jacky Lin
     */
    @Test
    void printToken() {
        // the print string should be 1336
        assertEquals("1336",m.printToken());
    }

    /**
     * test method for tokenChecker
     * @author Jacky Lin
     */
    @Test
    void tokenChecker() {
        // create a token list as user input
        Token[] testTokens = new Token[4];
        testTokens[0] = Token.G;
        testTokens[1] = Token.G;
        testTokens[2] = Token.P;
        testTokens[3] = Token.R;
        // check the result, all should be wrong
        assertEquals("____",m.tokenChecker(testTokens));
        // change another token list
        testTokens[0] = Token.O;
        testTokens[1] = Token.B;
        testTokens[2] = Token.Y;
        testTokens[3] = Token.O;
        // check the result, should be 4 correct color but in wrong place
        assertEquals("++++",m.tokenChecker(testTokens));
        // change another token list
        testTokens[0] = Token.B;
        testTokens[1] = Token.O;
        testTokens[2] = Token.O;
        testTokens[3] = Token.Y;
        // check the result, all tokens should be correct
        assertEquals("****",m.tokenChecker(testTokens));
    }
}