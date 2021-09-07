/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 10/17/19
 * Time: 10:43 AM
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

import hw01.game.MasterMind;
import hw01.game.Token;

import java.util.*;

/**
 * This is my personal designed hw01.solver for the MasterMind
 * <p>
 * 1. The first step of this method is to try 1111, 2222, 3333, 4444, 5555, and 6666
 * in order to test how many tokens is in the answer for each kind of tokens.
 * Through the first step, it will eliminate the impossible token sequence from
 * all possible solutions, according to their occurrence. For example, if Solver's
 * guess is 1111, and the Machine returns *___. It indicates that the token 1 occurs
 * once in the correct answer. Then it delete all the solutions that token 1 occurs
 * more or less than once.
 * <p>
 * 2. The second step is to test throughout the rest of the possible solutions,
 * which will be at most 24 situations. And if the answer is right , the test will be end.
 * <p>
 * This method will take at most 30 steps to figure out what is the answer.
 *
 * @author Jacky Lin, Yida Chen
 */
public class CustomSolver implements Solver {
    /**
     * the dataset of all possible solutions
     */
    private HashSet<String> possibleSol;
    /**
     * the current token to test
     */
    private int curToken;

    /**
     * The constructor of the Custom Solver
     *
     * @author Jacky Lin
     */
    public CustomSolver() {
        this.possibleSol = this.generateTokenSet();
        this.curToken = 0;
    }

    /**
     * This method helps to count how many correct tokens is in this guess
     *
     * @param res the test result to count
     * @return the number indicates the amount of correct tokens
     * @author Jacky Lin
     */
    public int countPossibleColor(String res) {
        int count = 0;
        for (String c : res.split("")) {
            if (c.equals("*") || c.equals("+")) { count++; }
        }
        return count;
    }

    /**
     * This method used to eliminate the impossible tokens in the dataset of all possible solutions
     *
     * @param count the number of correct tokens in the test.
     * @author Jacky Lin, Yida Chen
     */
    public void removeSolution(int count) {
        // the iterator helps to go over the HashSet
        Iterator<String> iter = this.possibleSol.iterator();
        // get the current token
        String currentToken = Token.values()[this.curToken].getNum();
        //remove impossible token from possibleSolution
        while (iter.hasNext()) {
            //get the next string
            String s = iter.next();
            //check the sequence of tokens and count for the current test tokens.
            int c = 0;
            for (String st : s.split("")) {
                if (st.equals(currentToken)) { c++; }
            }
            //remove the solution of with the wrong tokens
            if (c != count) { iter.remove(); }
        }
    }

    /**
     * This method is used to handle the result the machine give, it will also
     * add up the current position each time, to make sure the the guess in the right place
     * @param res the result of the tokenChecker
     * @author Jacky Lin
     */
    public void handleResult(String res) {
        int count = this.countPossibleColor(res);
        this.removeSolution(count);
        this.curToken ++;
    }

    /**
     * This method is designed to guess the answer, The first 6 times, it will generate the same 4 tokens
     * in order to eliminate all the impossible situation and the rest will remain to be selected in the next round.
     * The 7 times, it will following the order of the possible solution to guess the answer
     * @return a token list stands for the guess
     * @author Jacky Lin
     */
    @Override
    public Token[] generateGuessTokens() {
        Token[] guess = new Token[NUM_TOKENS];
        int len = Token.values().length;
        if (this.curToken < len) {
            Arrays.fill(guess, Token.values()[this.curToken]);
        } else {
            List<String> solList = new ArrayList<>(this.possibleSol);
            guess = this.readTokens(solList.get(this.curToken - len));
            this.curToken ++;
        }
        return guess;
    }

    /**
     * Getter method for current processing token
     * @return integer indicate the current token
     */
    public int getCurToken() {
        return curToken;
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
        CustomSolver cs = new CustomSolver();
        String res = st.INITIAL_RESPONSE;
        // String [ time, steps ]
        double[] stat = new double[2];
        //count time
        double start = System.nanoTime();
        while (!res.equals(MasterMind.SUCCESS)) {
            st.increCount();
            Token[] guess = cs.generateGuessTokens();
            res = st.machine.tokenChecker(guess);
            if (cs.getCurToken() < 6) {
                cs.handleResult(res);
            }
        }
        //get data
        double end = (System.nanoTime() - start) / NANO_TO_SECOND;
        stat[0] = end;
        stat[1] = st.getStepCount();
        return stat;
    }
}
