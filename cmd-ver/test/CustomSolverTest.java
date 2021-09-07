package hw01.test;

import hw01.solver.CustomSolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for the {@link CustomSolver}
 * @author Jacky Lin
 */
class CustomSolverTest {
    /** the solver instance for testing*/
    private CustomSolver cs;

    /**
     * instantiate the custom solver object
     * @author Jacky Lin
     */
    @BeforeEach
    void setUp() {
        cs = new CustomSolver();
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * test method for the count possible color
     * @author Jacky Lin
     */
    @Test
    void countPossibleColor() {
        assertEquals(2,cs.countPossibleColor("++__"));
        assertEquals(0,cs.countPossibleColor("____"));
        assertEquals(3,cs.countPossibleColor("**+_"));
    }
}