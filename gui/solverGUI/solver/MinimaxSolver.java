/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Yida Chen
 * Section: CSCI 205 9:00 AM
 * Date: 10/17/19
 * Time: 4:56 PM
 *
 * Project: csci205_hw
 * Package: hw01.solver
 * Class: minimaxSolver
 *
 * Description:
 *
 * ****************************************
 */
package hw02.solverGUI.solver;

import hw01.game.MasterMind;
import hw01.game.Peg;
import hw01.game.Token;

import java.util.*;

/**
 * MinimaxSolver class implement Knuth`s minimax algorithm to guess out
 * the code of mastermind. The solver always choose the unused guess with
 * the minimum scores of the maximum number of the possibilities it could
 * eliminate from the list with respect to all 15 possible responses from
 * mastermind (e.g. **+_).
 * @see <a href="https://github.com/nattydredd/Mastermind-Five-Guess-Algorithm">
 *     https://github.com/nattydredd/Mastermind-Five-Guess-Algorithm</a>
 * @author Yida Chen, Jacky Lin
 */
public class MinimaxSolver implements Solver {
    // minimaxCounter Map record the possible guess with its maximum score.
    private Map<String, Integer> minimaxCounter;

    // possibleSet
    private List<String> possibleSet;
    private Set<String> allPossibilities;
    private Set<String> possibleResponse;

    /**
     * The constructor of the minimaxSolver. When instantiate the hw01.solver, initiate and fill
     * the allPossibilities set with 1296 possible guesses, initiate the possibleSet list
     * with guess in allPossibilities Set (all 1296 guesses are possible at the beginning),
     * initiate the empty minimaxCounter HashMap, and initiate possibleResponse set contains all
     * possible response from the mastermind.
     * @author Yida Chen, Jacky Lin
     */
    public MinimaxSolver() {
        allPossibilities = this.generateTokenSet();
        possibleSet = new ArrayList<>(allPossibilities);
        minimaxCounter = new HashMap<>();
        possibleResponse = generateResult();
    }

    /**
     * Calculate the maximum elimination score of each guess. The score of a guess is the
     * maximum number of impossible guess (choice) eliminated by the guess in all possible responses.
     * The method put the guess with its maximum scores into the minimaxCounter of the class.
     * @see <a href="https://github.com/nattydredd/Mastermind-Five-Guess-Algorithm">
     *     https://github.com/nattydredd/Mastermind-Five-Guess-Algorithm</a>
     * @author Yida Chen
     */
    private void calculateScores() {
        // Clear the minimaxCounter before each calculations
        clearMinimaxCounter();
        for (String possibleChoice : allPossibilities) {
            int maxScore = 0;
            // Calculate the score of the guess with all possible responses (e.g. *+__)
            for (String response : possibleResponse) {
                /** The score of a guess is the number of possible guesses can be eliminated by it
                 *  with each possible response
                 */
                int score = getImpossibleChoice(possibleChoice, response).size();
                if (score > maxScore) {
                    // We look for its maximum scores
                    maxScore = score;
                }
            }

            // Put the guess and its score into the counter map
            minimaxCounter.put(possibleChoice, maxScore);
        }
    }

    /**
     * Clear the HashMap minimaxCounter
     * @author Yida Chen
     */
    private void clearMinimaxCounter() {
        // Clear the map
        minimaxCounter.clear();
    }


    /**
     * Find the minimax choice for the next guess. Choose the guess with the minimum max elimination score
     * as the next guess.
     * @see <a href="https://github.com/nattydredd/Mastermind-Five-Guess-Algorithm">
     *     https://github.com/nattydredd/Mastermind-Five-Guess-Algorithm</a>
     * @return A String contains the minimax choice for the next guess.
     * @author Yida Chen
     */
    private String minimumMaxChoice() {
        String resultGuess = "";
        // maximum score will not exceed 1297 as there are only 1296 guesses in the Set
        int minimumMaxScore = 1297;
        // Check whether the possible set is empty
        boolean getGuessInSet = !possibleSetIsEmpty();

        for (Map.Entry<String, Integer> choices : minimaxCounter.entrySet()) {
            if (choices.getValue() < minimumMaxScore) {
                if(getGuessInSet){
                    // Use a guess from the possibleSet whenever possible
                    if(possibleSet.contains(choices.getKey()))
                        minimumMaxScore = choices.getValue();
                        resultGuess = choices.getKey();
                }
                else {
                    // If the possibleSet is empty, use an unused guess from allPossibilities with minimum score
                    minimumMaxScore = choices.getValue();
                    resultGuess = choices.getKey();
                }
            }
        }

        // Return the result guess
        return resultGuess;
    }

    /**
     * Get the next guess for the code.
     * @param previousGuess A String contains the hw01.solver`s previous guess to the code.
     * @param response A String contains the mastermind`s response to the hw01.solver`s previous
     *                 guess
     * @return A String contains the hw01.solver`s next guess to the code.
     * @author Yida Chen, Jacky Lin
     */
    public String getNextGuess(String previousGuess, String response) {
        /** Reset the possibleSet if the set is empty
         *  This could happen if all choice was eliminate before the
         *  solver guess out the code.
         */
        if (possibleSetIsEmpty()){
            resetPossibleSet();
        }

        // Remove the previous guess from unused guess
        allPossibilities.remove(previousGuess);

        // Remove the previous guess from possible guess
        possibleSet.remove(previousGuess);

        // Remove the impossible guess based on previous guess and response
        removeFromList(getImpossibleChoice(previousGuess, response));

        // Calculate the scores of the remaining unused guess
        calculateScores();

        // Get the next guess based on their scores
        String nextGuess = minimumMaxChoice();

        // Return the next guess
        return nextGuess;
    }

    /**
     * Get all the impossibleChoice eliminate by the previous guess and the mastermind`s response
     * to the previous guess. If the response returned by checkCode method is not equal to the
     * mastermind`s response, the test guess is an impossible choice.
     * @see <a href="https://github.com/nattydredd/Mastermind-Five-Guess-Algorithm">
     *     https://github.com/nattydredd/Mastermind-Five-Guess-Algorithm</a>
     * @param guess A String contains the previous guess the hw01.solver tried.
     * @param response A String contains the response to the previous guess.
     * @return A List of Strings contain the impossible guesses.
     * @author Yida Chen
     */
    private List<String> getImpossibleChoice(String guess, String response) {

        List<String> removedList = new ArrayList<>();

        // Check the impossible choice in the possibleSet
        for (String possibleChoice : possibleSet) {
            /** If the response from checkCode is different from that from mastermind,
             *  add this guess into the removedList as it is impossible.
             */
            if (!checkCode(possibleChoice, guess).equals(response)) {
                removedList.add(possibleChoice);
            }
        }

        // Return a List of Strings contain the impossible guesses.
        return removedList;
    }

    /**
     * Assume current code (guess) is the correct code, check the response of the test guess
     * based on this assumption. E.g. Current code is 1124, and the test choice is 1141, then the
     * response will be **+_.
     * @see <a href="https://github.com/nattydredd/Mastermind-Five-Guess-Algorithm">
     *    https://github.com/nattydredd/Mastermind-Five-Guess-Algorithm</a>
     * @param guess A String contains test guess from the all unused guess.
     * @param code A String contains the previous guess to the code.
     * @return A String contains he response of the test guess if we assume the previous guess is the
     * correct code.
     * @author Yida Chen
     */
    public String checkCode(String guess, String code) {
        String result = "";

        // Get the array of char from the test guess
        char[] checkGuess = guess.toCharArray();
        // Get the array of char from the check code (previous guess)
        char[] checkCode = code.toCharArray();

        // Check * peg
        for (int i = 0; i < checkCode.length; i++) {
            if (checkGuess[i] == checkCode[i]) {
                // If same spot and same token, add * to the response
                result += "*";
                // And mark the token in guess and token in code as checked
                checkGuess[i] = 'C';
                checkCode[i] = 'C';
            }
        }

        // Check + peg
        for (int i = 0; i < checkCode.length; i++) {
            // If the token in code is unchecked
            if (checkCode[i] != 'C') {
                for (int j = 0; j < checkGuess.length; j++) {
                    if (checkGuess[j] == checkCode[i]) {
                        /** And there is uncheck token in test guess is same to the unchecked
                         *  token in code. Add the + to the response.
                         */
                        result += "+";
                        // Mark the token in guess as checked
                        checkGuess[j] = 'C';
                    }
                }
            }
        }

        /** Filled the rest spot in result with _ peg, as there is no more match between
         *  the test guess and the code (previous guess)
         */
        for (int i = result.length(); i < checkGuess.length; i++) {
            result += "_";
        }

        // Return the result.
        return result;
    }

    /**
     * Generate all possible checked result from ____ to ****
     * there are total 15 situations according to mathematical calculations
     * @return a {@link HashSet} contains all situations as String.
     * @author Jacky Lin
     */
    private static HashSet<String> generateResult() {
        HashSet<String> resSet = new HashSet<>();
        int numPeg = Peg.values().length;
        for (int i = 0; i < numPeg; i++) {
            String pegs = Peg.values()[numPeg - i - 1].getName();
            for (int j = 0; j <= i; j++) {
                pegs += Peg.values()[numPeg - j - 1].getName();
                for (int k = 0; k <= j; k++) {
                    pegs += Peg.values()[numPeg - k - 1].getName();
                    for (int z = 0; z <= k; z++) {
                        pegs += Peg.values()[numPeg - z - 1].getName();
                        resSet.add(pegs);
                        pegs = pegs.substring(0, 3);
                    }
                    pegs = pegs.substring(0, 2);
                }
                pegs = pegs.substring(0, 1);
            }
        }
        return resSet;
    }

    /**
     * Remove the list of impossible guesses from the possible guess set.
     * @param removedList A List of Strings contain the impossible guesses to remove
     *                    from the possibleSet
     * @author Yida Chen
     */
    private void removeFromList(List<String> removedList) {
        for (String impossible : removedList) {
            possibleSet.remove(impossible);
        }
    }

    /**
     * Check whether the possibleSet is empty
     * @return true if the set is empty otherwise false
     * @author Yida Chen
     */
    private boolean possibleSetIsEmpty(){
        return possibleSet.size() == 0;
    }

    /**
     * Reset the possibleSet with all unused guesses in allPossibilities set
     * @author Jacky Lin
     */
    private void resetPossibleSet(){
        possibleSet = new ArrayList<>(allPossibilities);
    }

    /**
     * Read in the guess represented by an array of tokens, and transfer them into a String
     * with 1 - 6 representing the B, G..P tokens.
     * @param tokens An array contains token guess
     * @return A String form of the guess
     * @author Yida Chen
     */
    public String tokensToString(Token[] tokens){
        String result = "";
        for (Token token: tokens){
            // Transfer the token representation of guess into the String representation
            result += token.getNum();
        }
        return result;
    }

    /**
     * This method is used to generate a list of tokens as guess
     * @return a list of tokens
     * @author Jacky Lin
     */
    @Override
    public Token[] generateGuessTokens() {
        Token[] tokens = {Token.B, Token.B, Token.G, Token.G};
        return tokens;
    }

    /**
     * Simulate the mastermind game with the minimaxSolver
     * @param st SolverSimulator object
     * @return A double array contains the statistics value about the
     * time and steps that solver used to finish the game
     * @author Jacky Lin
     */
    @Override
    public double[] solverSimulation(SolverSimulator st) {
        //count time
        double start = System.nanoTime();

        //initialization
        MinimaxSolver ms = new MinimaxSolver();

        // Statistics [ time, steps ]
        double[] stat = new double[2];

        // Initial guess and its response
        String guess = tokensToString(generateGuessTokens());
        String res = st.machine.tokenChecker(this.readTokens(guess));
        // Increment the number of steps
        st.increCount();

        while (!res.equals(MasterMind.SUCCESS)) {
            // If the guess is wrong, make the next guess and increment the number of steps
            st.increCount();
            // Get the next guess from the hw01.solver
            guess = ms.getNextGuess(guess, res);
            // Get the response to the guess from the Mastermind
            res = st.machine.tokenChecker(this.readTokens(guess));
        }

        // Guess out the code, record the finish time and steps
        double end = (System.nanoTime() - start) / NANO_TO_SECOND;

        // unit in second
        stat[0] = end;
        // Record the used steps
        stat[1] = st.getStepCount();

        // Return the statistics
        return stat;
    }
}

