package hw01.test;

import hw01.net.MastermindProtocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * the test for the {@link MastermindProtocol} class
 * @author Jacky Lin
 */
class MastermindProtocolTest {
    /** the MasterMindProtocol Object for test*/
    private MastermindProtocol mp;

    /**
     * initialize the object before testing
     * @author Jacky Lin
     */
    @BeforeEach
    void setUp() {
        //3 player in game
        mp = new MastermindProtocol(3);
    }

    /**
     * it will check some string input and compare with the expected result
     * @author Jacky Lin
     */
    @Test
    void runGame() {
        assertEquals("",mp.runGame("NAME:"));
        assertEquals("Following players joining the game: 1.",mp.runGame("DISPLAY_PLAYERS"));
        assertEquals("WAIT",mp.runGame("STAT"));
        assertEquals("WAIT",mp.runGame("AGAIN"));
        assertEquals("WAIT",mp.runGame("RESTART"));
        assertEquals("QUIT",mp.runGame("STOP"));
        assertEquals("QUIT",mp.runGame("QUIT"));
    }
}