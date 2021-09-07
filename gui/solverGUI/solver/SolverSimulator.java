/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 10/17/19
 * Time: 10:15 AM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package hw02.solverGUI.solver;

import hw01.game.Machine;

/**
 * This class help test all solver mode, it connected them in one class,
 * including {@link RandomSolver}, {@link MinimaxSolver}, and {@link CustomSolver}
 * @author Jacky Lin, Yida Chen
 */
public class SolverSimulator {
    /** the step count*/
    private int stepCount;
    /** the machine of game*/
    public Machine machine;
    /** initial result of testing*/
    public final String INITIAL_RESPONSE = "____";

    /**
     * the constructor for the simulator, it initialize the machine object.
     * @author Jacky Lin
     */
    public SolverSimulator() {
        this.stepCount = 0;
        machine = new Machine();
        machine.generateTokens();

    }

    /**
     * this method simulate the specific solver assigned and run given number turns.
     * @param s the solver to simulate
     * @param tries the total number of trial to take
     * @author Jacky Lin, Yida Chen
     */
    public static void simulate(Solver s, int tries) {
        //initialization for stats parameters
        int gameCounter = 0;
        int totalSteps = 0;
        double totalTime = 0;
        double shortest = 10000;
        double longest = 0;

        //start test
        while (gameCounter < tries ) {
            SolverSimulator st = new SolverSimulator();
            Solver solver = s;
            double[] res = s.solverSimulation(st);
            //record data
            totalTime += res[0];
            totalSteps += res[1];
            if (res[1] > longest) {
                longest = res[1];
            }
            if (res[1] < shortest) {
                shortest = res[1];
            }
            //count game
            gameCounter++;
        }
    }

    /**
     * getter method for the count
     * @return the count indicate the steps one test take
     * @author Jacky Lin
     */
    public int getStepCount() {
        return stepCount;
    }

    /**
     * increase the count by 1
     * @author Jacky Lin
     */
    public void increCount() {
        this.stepCount++;
    }
}
