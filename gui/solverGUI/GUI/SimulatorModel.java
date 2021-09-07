/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 10/30/19
 * Time: 9:07 AM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package hw02.solverGUI.GUI;

/**
 * SimulatorModel class which represent the model of the simulator.
 * The methods of this class represent the working logic of the simulator
 * @author Yida Chen, Jacky Lin
 */
public class SimulatorModel {
    /**
     * Constructor of the SimulatorModel class
     * @author Yida Chen
     */
    public SimulatorModel() {
    }

    /**
     * Get the number of simulations (tries) from the user input.
     * @param tries String input contains the number of simulations from the user.
     * @return int number of simulations of the game
     * @author Yida Chen
     */
    public int getNumOfTries(String tries){
        int numOfTries = 0;
        if (tries.length() > 0){
            numOfTries = Integer.parseInt(tries);
            if (numOfTries < 0){
                numOfTries = 0;
            }
        }
        return numOfTries;
    }

    /**
     * Get the index of the bin, which data belong to, in the X axis bin series
     * @param data The data that belong to that bin
     * @param upperBoundOfChart The upper bound of the bar chart
     * @param binWidth The width of the bin in the bar chart
     * @return int value represent the index of the bin in the X axis bin series
     * @author Yida Chen
     */
    public int getBinNum(double data, int upperBoundOfChart, int binWidth) {
        if (data > upperBoundOfChart){
            data = upperBoundOfChart + 1;
        }
        int bin = (int) (data - 1) / binWidth;
        return bin;
    }

    /**
     * Get the category of the X axis in the bar chart
     * @param binWidth The width of the bin in the bar chart
     * @param binLowerBound The lower bound of the bin
     * @return A String contains the category in the bar chart.
     * @author Yida Chen
     */
    public String getCategory(int binWidth, int binLowerBound) {
        return binLowerBound + " - " + (binLowerBound + binWidth);
    }
}
