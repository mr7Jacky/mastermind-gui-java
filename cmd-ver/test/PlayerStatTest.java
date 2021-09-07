package hw01.test;

import hw01.net.PlayerStat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test for {@link PlayerStat}
 * @author Yida Chen
 */
class PlayerStatTest {
    // ArrayList contains test PlayerStat objects
    private ArrayList<PlayerStat> playerStats;

    /**
     * Set up the ArrayList playerStats that contains the test PlayerStat objects
     * @author Yida Chen
     */
    @BeforeEach
    void setUp() {
        // Initiate the ArrayList and the PlayerStat objects
        playerStats = new ArrayList<>();
        PlayerStat romeo = new PlayerStat("Romeo","WON",20.0,8);
        PlayerStat juliet = new PlayerStat("Juliet", "WON", 30.0, 5);
        PlayerStat montagues = new PlayerStat("Montagues", "FAILED", 10.0,13);
        PlayerStat shakespeare = new PlayerStat("Shakespeare", "WON", 20.0, 5);
        playerStats.add(romeo);
        playerStats.add(juliet);
        playerStats.add(montagues);
        playerStats.add(shakespeare);
    }

    @AfterEach
    void tearDown() {
    }


    /**
     * Test the shortTestTime method which should return a String contains the
     * statistics information of the player who win the game in shortest time
     * @author Yida Chen
     */
    @Test
    void shortTestTime() {
        // Romeo win the game with shortest time, and Shakespeare is the tie winner. Although montagues use less time, he did not when the game.
        assertEquals("Success using shortest Time: Romeo with 20.00 s, Shakespeare with 20.00 s",PlayerStat.shortTestTime(playerStats));
    }

    /**
     * Test the minSteps method which should return a String contains the
     * statistics information of the player who win the game in minimum steps
     * @author Yida Chen
     */
    @Test
    void minSteps() {
        // Juliet win the game with minimum steps. She won used only 5 steps, and the Shakespeare is the tie winner.
        assertEquals("Success in minimum steps: Juliet with 5 steps, Shakespeare with 5 steps",PlayerStat.minSteps(playerStats));
    }
}