/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 10/17/19
 * Time: 9:52 AM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package hw01.solver;

import hw01.game.MasterMind;
import hw01.game.Token;

import java.util.Random;

/**
 * This class will randomly guess codes until it happens to create a match.
 * @author Jacky Lin, Yida Chen
 */
public class RandomSolver implements Solver{

    /**
     * use {@link Random} to generate the random tokens from {@link Token} to guess the correct one
     * @return return a token list of guessed tokens
     * @author Jacky Lin
     */
    @Override
    public Token[] generateGuessTokens(){
        Token[] guessResult = new Token[NUM_TOKENS];
        for (int i=0; i<4; i++){
            int ran = new Random().nextInt(Token.values().length);
            guessResult[i] = Token.values()[ran];
        }
        return guessResult;
    }

    /**
     * the method to run the simulate the solver once, it will generate a double list to
     * store the data including the time and steps for this solver tot run once
     * @param st the solver simulator {@link SolverSimulator} for simulation
     * @return double list to store the time and steps for this solver tot run once
     * @author Jacky Lin
     */
    @Override
    public double[] solverSimulation(SolverSimulator st) {
        //initialization
        RandomSolver rs = new RandomSolver();
        String res = st.INITIAL_RESPONSE;
        // String [ time, steps ]
        double[] stat = new double[2];
        //count time
        double start = System.nanoTime();
        while (!res.equals(MasterMind.SUCCESS)) {
            st.increCount();
            Token[] guess = rs.generateGuessTokens();
            res = st.machine.tokenChecker(guess);
        }
        double end = (System.nanoTime() - start) / NANO_TO_SECOND;
        stat[0] = end;
        stat[1] = st.getStepCount();
        return stat;
    }
}
