/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 10/17/19
 * Time: 11:11 AM
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

import hw01.game.Machine;
import hw01.game.Token;

import java.util.HashSet;

/**
 * This class provides a series of method for classes of Solver to use
 * It also includes some constant for classes that implemented it.
 * @author Jacky Lin, Yida Chen
 */
public interface Solver {

    /** the number fo tokens generates*/
    int NUM_TOKENS = Machine.TOKEN_NUM;

    /** From nanosecond to second */
    double NANO_TO_SECOND = 10e9;

    /**
     * This method is used to generate a list of tokens as guess
     * @return a list of tokens
     * @author Jacky Lin
     */
    Token[] generateGuessTokens();

    /**
     * This method is used to simulate the game with solvers
     * @param st SolverSimulator to run for the simulation
     * @author Jacky Lin
     */
    double[] solverSimulation(SolverSimulator st);
    /**
     * This method generate a HashSet {@link HashSet} of all possible solutions for the mastermind game
     * @return the HashSet contains all the possible solutions
     * @author Yida Chen
     */
    default HashSet<String> generateTokenSet(){
        HashSet<String> tokenSet = new HashSet<>();
        int numToken = Token.values().length;
        for(int i = 1; i <= numToken; i++){
            Integer tokens = i*1000;
            for(int j = 1; j <= numToken; j++){
                tokens += j * 100;
                for(int k = 1; k <= numToken; k++){
                    tokens += k * 10;
                    for(int z = 1; z <= numToken; z++){
                        tokens += z;
                        tokenSet.add(tokens.toString());
                        tokens -= z;
                    }
                    tokens -= k * 10;
                }
                tokens -= j * 100;
            }
        }
        return tokenSet;
    }
    /**
     * this method convert a token list to a String
     * @param input the tokens that user choose
     * @return a list of tokens that the user input
     * @author Jacky Lin
     */
    default Token[] readTokens(String input){
        int length = input.length();
        Token[] inputTokens = new Token[length];
        for (int i=0; i<length; i++) {
            char num = input.toCharArray()[i];
            int a = Integer.parseInt(String.valueOf(num));
            inputTokens[i] = Token.values()[a-1];
        }
        return inputTokens;
    }
}
