/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52AM
 * Date: 10/4/19
 * Time: 3:03 PM
 *
 * Project: Java
 * Package: Lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */
package hw01.game;

/**
 * MasterMind class represent the game, it includes some method used to play the game
 * @author Jacky Lin, Yida Chen
 */
public class MasterMind {
    /** the success result to win the game */
    public final static String SUCCESS = "****";
    /** total guess of one game*/
    public static final int TOTAL_GUESS = 12;
    /** lowest number of the token*/
    public static final int LOWEST_NUM = 0;
    /** th count of guess time*/
    public static int guessCount = 1;
    /** a string to store the correct answer for the game*/
    private static String correctAnswer;
    /** a boolean indicate whether to continue a game or not*/
    private static boolean gameRunning = true;

    /**
     * this method is print the result
     * @param input user guess
     * @param checkResult the result of the guess
     * @author Jacky Lin
     */
    public static void displayResult(String input, String checkResult) {
        if (isSuccess(checkResult)){
            System.out.printf("%s --> %4s YOU WON! You guessed the code in %s moves!%n",
                    input,checkResult, guessCount);
            gameRunning = false;
        }
        else if (guessCount == TOTAL_GUESS) {

            System.out.printf("%s --> %4s Game Over! You have tried %s moves!%nThe answer is: %s%n",
                    input, checkResult, guessCount, correctAnswer);
            gameRunning = false;
        }
        else {
            System.out.printf("%s --> %4s Try again. %s guesses left%n",
                    input,checkResult,TOTAL_GUESS - guessCount);
        }
    }

    /**
     * this method is to read the tokens that a user input and check if there is any illegal input
     * @param input the tokens that user choose
     * @param inputLength the max length that the input string can have
     * @return a list of tokens that the user input
     * @throws InputLengthException this exception throws when the length of input series of num is too long
     * @throws IllegalInputException this exception throws when there is illegal words inputed
     * @author Jacky Lin
     */
    public static Token[] readTokens(String input, int inputLength) throws InputLengthException, IllegalInputException {
        int length = input.length();
        if (length != inputLength) {
            throw new InputLengthException("Input more than 4 tokens!");
        }
        Token[] inputTokens = new Token[length];
        for (int i=0; i<length; i++) {

            char num = input.toCharArray()[i];
            if (!Character.isDigit(num)) {
                throw new IllegalInputException("Please input contains only number");
            }
            int a = Integer.parseInt(String.valueOf(num));
            if (a > Token.values().length || a <= LOWEST_NUM){
                throw new IllegalInputException("Input number is not in 1 to 6");
            }
            inputTokens[i] = Token.values()[a-1];
        }
        return inputTokens;
    }

    /**
     * this method check if the user is win
     * @param result check the result with the success condition
     * @return the boolean indicate whether it is success.
     * @author Yida Chen
     */
    private static boolean isSuccess(String result) {
        return result.equals(SUCCESS);
    }
    /**
     * check if the game is running
     * @return true if game is running. else it is false
     * @author Yida Chen
     */
    public static boolean isGameRunning() {
        return gameRunning;
    }
    /**
     * set the correct answer
     * @author Yida Chen
     */
    public static void setCorrectAnswer(String correctAnswer) {
        MasterMind.correctAnswer = correctAnswer;
    }

    /**
     * reset the guessCount when restarting game
     * @author Jacky Lin
     */
    public static void resetGuessCount() {
        MasterMind.guessCount = 1;
    }
    /**
     * set the gameRunning to true when restarting game
     * @author Jacky Lin
     */
    public static void resetGameRunning() {
        MasterMind.gameRunning = true;
    }
    /**
     * InputLengthException is thrown when the input length is larger than the expected length 4
     * @author Jacky Lin
     */
    public static class InputLengthException extends Exception {

        /**
         * constructor for the inputLengthException
         * @author Jacky Lin
         */
        public InputLengthException( String massage) {
            super(massage);
        }
    }

    /**
     * IllegalInputException is thrown when the input contains illegal words.
     * @author Jacky Lin
     */
    public static class IllegalInputException extends Exception {

        /**
         * constructor for the IllegalInputException
         * @author Jacky Lin
         */
        public IllegalInputException( String massage) {
            super(massage);
        }
    }
}