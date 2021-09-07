package hw02.test;

import hw02.solverGUI.GUI.SimulatorModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test class for the SimulatorModel class
 * Test the getNumOfTries, getBinNum, and getCategory methods of SimulatorModel class
 * @author Yida Chen
 */
class SimulatorModelTest {
    /** Test SimulatorModel object */
    private SimulatorModel theModel;

    /**
     * Set up the Model before each test
     * @author Yida Chen
     */
    @BeforeEach
    void setUp() {
        theModel = new SimulatorModel();
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Test the getNumOfTries method of the model class
     * This method strip out the number of tries from a String
     * input of user.
     * @author Yida Chen
     */
    @Test
    void getNumOfTries() {
        String testTries = "42";
        assertEquals(42, theModel.getNumOfTries(testTries));
    }

    /**
     * Test the getBinNum method of the model class
     * This method return the index of the bin that data belongs to in the bin list.
     * @author Yida Chen
     */
    @Test
    void getBinNum() {
        int upperBound = 20;
        int binWidth = 2;
        // Data within the upper bound and it is in the bin "16 - 18" which is 9th bin.
        int testData1 = 17;

        // Data out of the upper bound. It will be placed in the last bin of the chart
        // Data in the bin "> 20"
        int testData2 = 21;

        /** Test with the data within the bound and in the 9th bin. 9th bin correspond to
         *  index 8 of the list.
         */
        assertEquals(8, theModel.getBinNum(testData1, upperBound, binWidth));

        /** Test with the data out of the bound and in the last bin of the chart which correspond
         *  to the index 10 of the list
         */
        assertEquals(10, theModel.getBinNum(testData2, upperBound, binWidth));
    }

    /**
     * Get the category on the x axis of the bar chart based on the lower bound
     * of the current bin and the bin width.
     * E.g. if the lower bound of the current bin is 350 and the bin width is 50
     * then the category should be "350 - 400"
     * @author Yida Chen
     */
    @Test
    void getCategory() {
        String expectedCate = "100 - 200";
        int currentLowerBound = 100;
        int binWidth = 100;
        assertEquals(expectedCate, theModel.getCategory(binWidth, currentLowerBound));
    }
}