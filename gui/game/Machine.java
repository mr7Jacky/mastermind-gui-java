/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52AM
 * Date: 10/4/19
 * Time: 9:25 PM
 *
 * Project: Java
 * Package: Lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */
package hw02.game;

import hw02.game.chess.Peg;
import hw02.game.chess.Token;
import hw02.game.chess.TokenEnum;

import java.util.ArrayList;
import java.util.Random;

/**
 * Machine class represent the MasterMind Machine, with a series of functions
 * @author Jacky Lin
 */
public class Machine {
    /** the number of token in each game */
    private int tokenNum;
    /** it is a list of token generate from generateTokens */
    private TokenEnum[] tokenEnums;
    /** token counter to record the number of color in tokens */
    private TokenCounter expectCounter;

    /**
     * constructor for the mastermind class
     * it initialize the token num and tokens list
     * @author Jacky Lin
     */
    public Machine(int tokenNum) {
        this.tokenNum = tokenNum;
        this.tokenEnums = new TokenEnum[tokenNum];
    }

    /**
     * use {@link Random} to generate the random tokens from {@link TokenEnum}
     * this method stores data in the tokens
     * @author Jacky Lin
     */
    public void generateTokens(){
        expectCounter = new TokenCounter();
        for (int i = 0; i< tokenNum; i++){
            int ran = new Random().nextInt(TokenEnum.values().length);
            TokenEnum t = TokenEnum.values()[ran];
            expectCounter.addToken(t);
            tokenEnums[i] = t;
        }
    }

    /**
     * check the token the user gave with the expected answer from the machine
     * @param inputToken the token user choose to check if it matches the expected tokens
     * @return the result peg given the check result
     * @see <a href="https://stackoverflow.com/questions/10078867/how-to-initialize-all-the-elements-of-an-array-to-any-specific-value-in-java">
     *     https://stackoverflow.com/questions/10078867/how-to-initialize-all-the-elements-of-an-array-to-any-specific-value-in-java</a>
     * @author Jacky Lin
     */
    public ArrayList<Peg> tokenChecker(ArrayList<Token> inputToken) {
        //initialize a resultPeg list of 4 elements indicate the true or false
        ArrayList<Peg> resultPeg = new ArrayList<>();
        //fill the resultPeg with wrong answer object
        for (int i = 0; i < inputToken.size(); i++) {
            resultPeg.add(new Peg());
        }
        //create a new TokenCounter for the inputTokens
        TokenCounter inputCounter = new TokenCounter();
        //check the position of tokens
        //correctPosition record the num of position in the list processed
        int correctPosition = 0;
        for (int i = 0; i< tokenNum; i++) {
            if (inputToken.get(i).getSelectedColor().equals(tokenEnums[i].getColor())) {
                resultPeg.get(correctPosition).showStarPeg();
                correctPosition++;
            }
            //record the input token number
            inputCounter.addToken(inputToken.get(i).getTokenEnum());
        }
        //check the number of tokens with the right color
        int correctColor = expectCounter.numberEquals(inputCounter);
        for (int i = correctPosition; i < correctColor ; i++) {
            //fill the rest of slot of U
            resultPeg.get(i).showPlusPeg();
        }
        return resultPeg;
    }

    /**
     * get tokens list
     * @return token list
     */
    public TokenEnum[] getTokenEnums() {
        return tokenEnums;
    }

    /**
     * the token class for count the color in the toke list
     * @author Jacky Lin
     */
    public static class TokenCounter {
        /** a list of count for tokens */
        /** the order of the list is B,G,O,P,R,Y same order as {@link TokenEnum} enum*/
        private int[] counter;
        /** the number of the colors */
        private final int COLOR_NUM = TokenEnum.values().length;

        /**
         * constructor for the tokenCounter class
         * @author Jacky Lin
         */
        public TokenCounter() {
            this.counter = new int[COLOR_NUM];
        }

        /**
         * add count of a certain color according to the token given.
         * @param t the token to add
         * @author Jacky Lin
         */
        public void addToken(TokenEnum t){
            this.counter[t.ordinal()] ++;
        }

        /**
         * get counter list
         * @return counter list
         * @author Jacky Lin
         */
        public int[] getCounter() {
            return this.counter;
        }

        /**
         * the method count the number of same color between two tokenCounters{@link TokenCounter}
         * @param tokenCounter the {@link TokenCounter}object to compare to
         * @return a int indicate the number of same color
         * @author Jacky Lin
         */
        public int numberEquals (TokenCounter tokenCounter) {
            int c = 0;
            for (int i=0; i <COLOR_NUM; i++){
                c += Math.min(tokenCounter.getCounter()[i], this.counter[i]);
            }
            return c;
        }
    }
}