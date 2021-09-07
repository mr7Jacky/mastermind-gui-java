package hw01.test;

import hw01.net.ClientPlayer;
import hw01.net.GameUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test method for the {@link GameUtility}
 * @author Jacky Lin
 */
class GameUtilityTest {
    /** the GameUtility object to test*/
    private GameUtility GU;

    /** represent the user input*/
    private Scanner in;

    /**
     * initialize the game utility
     * @author Jacky Lin
     */
    @BeforeEach
    void setUp() {
        GU = new GameUtility();
    }

    /**
     * test the method getNumPlayer
     * @author Jacky Lin
     */
    @Test
    void isSingleMode() {
        in  = new Scanner("S");
        assertTrue(GU.isSingleMode(in));
        in  = new Scanner("s");
        assertTrue(GU.isSingleMode(in));
        in  = new Scanner("M");
        assertFalse(GU.isSingleMode(in));
        in  = new Scanner("M");
        assertFalse(GU.isSingleMode(in));

    }

    /**
     * test the method getNumPlayer
     * @author Jacky Lin
     */
    @Test
    void hostingGame() {
        in  = new Scanner("y");
        assertTrue(GU.hostingGame(in));
        in  = new Scanner("Y");
        assertTrue(GU.hostingGame(in));
        in  = new Scanner("N");
        assertFalse(GU.hostingGame(in));
        in  = new Scanner("n");
        assertFalse(GU.hostingGame(in));

    }

    /**
     * test the method getNumPlayer
     * @author Jacky Lin
     */
    @Test
    void getPortNum() {
        // regular check
        in  = new Scanner("1234");
        assertEquals(1234,GU.getPortNum(in));
    }

    /**
     * test the method getNumPlayer
     * @author Jacky Lin
     */
    @Test
    void getNumPlayers() {
        // regular check
        Scanner in  = new Scanner("3");
        assertEquals(3,GU.getNumPlayers(in));
    }
}