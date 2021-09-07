package hw01.test;

import hw01.game.MasterMind;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test class for class {@link MasterMind}
 * @author Jacky Lin
 */
class MasterMindTest {
    /** a mastermind object for test*/
    private MasterMind mm;

    /**
     * initialize the mastermind object before testing
     * @author Jacky Lin
     */
    @BeforeEach
    void setUp() {
        mm = new MasterMind();
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * test for readTokens method, which may throw exception
     * @author Jacky Lin
     */
    @Test
    void readTokens() {
        // if input too many tokens it will throw InputLengthException
        assertThrows(MasterMind.InputLengthException.class,
                ()-> mm.readTokens("1111111",4));
        // if input string contains not only number it will throw IllegalInputException
        assertThrows(MasterMind.IllegalInputException.class,
                ()-> mm.readTokens("abcd",4));
        // if input tokens larger than 6 it will throw IllegalInputException
        assertThrows(MasterMind.IllegalInputException.class,
                ()-> mm.readTokens("9999",4));
    }
}