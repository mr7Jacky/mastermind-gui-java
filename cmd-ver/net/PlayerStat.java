/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Yida Chen
 * Section: CSCI 205 9:00 AM
 * Date: 10/14/19
 * Time: 4:02 PM
 *
 * Project: csci205_hw
 * Package: hw01.net
 * Class: playerStat
 *
 * Description:
 *
 * ****************************************
 */
package hw01.net;

import java.util.ArrayList;

/**
 * PlayerStat class represent the statistics data of the Player, include the name of the player,
 * the end of game result (win or failed), the finish time in seconds, and the steps the player used
 * to finish the game.
 * It contains methods shortTestTime, which could return the statistics of the player who won with
 * shortest and minStep, and minSteps, which could return the statistics of the player who won with
 * minimum steps.
 * @author Yida Chen, Jacky Lin
 */
public class PlayerStat {
    /** store the result*/
    private String endOfGameResult;
    /** record the finish time*/
    private double finishTime;
    /** record the steps*/
    private int step;
    /** record the name of the player*/
    private String playerName;

    /**
     * The Constructor of the playerStat class
     * @param playerName The name of the player
     * @param endOfGameResult The end of game result, win or failed
     * @param finishTime The time that player used to finish the game
     * @param step The steps that player used to finish the game
     * @author Yida Chen, Jacky Lin
     */
    public PlayerStat(String playerName, String endOfGameResult, double finishTime,  int step){
        this.playerName = playerName;
        this.finishTime = finishTime;
        this.endOfGameResult = endOfGameResult;
        this.step = step;
    }

    /**
     * Return the string that contains the statistics of player who wins with
     * shortest time. Return "No player win the game." if no player win the game.
     * @param playerStats The playerStats ArrayList contains a list of playerStat object
     *                    which contains the statistic information of a player
     * @return A string with the information of the player who finished the game in minimum
     * amount of the time. Return "No player win the game." if no player win the game.
     * @author Yida Chen
     */
    public static String shortTestTime(ArrayList<PlayerStat> playerStats){
        double shortTime = 1.0E12;
        String playerName;
        String result = "No player win the game.";
        for(PlayerStat playerStat: playerStats){
            if(playerStat.getFinishTime() == shortTime && playerStat.getEndOfGameResult().equals("WON")){
                result += String.format(", %s with %.2f s",playerStat.getPlayerName(),playerStat.getFinishTime());
            }
            else if(playerStat.getFinishTime() < shortTime && playerStat.getEndOfGameResult().equals("WON")){
                shortTime = playerStat.getFinishTime();
                playerName = playerStat.getPlayerName();
                result = String.format("Success using shortest Time: %s with %.2f s",playerName, shortTime);
            }
        }
        return result;
    }

    /**
     * Return the string that contains the statistics of player who wins with
     * minimum steps. Return "No player win the game." if no player win the game.
     * @param playerStats
     * @return Return the string that contains the statistics of player who wins with
     * minimum steps. Return "No player win the game." if no player win the game.
     * @author Yida Chen
     */
    public static String minSteps(ArrayList<PlayerStat> playerStats){
        int steps = 13;
        String result = "No player win the game.";
        String playerName;
        for(PlayerStat playerStat: playerStats){
            if(playerStat.getStep() == steps && playerStat.getEndOfGameResult().equals("WON")){
                result += ", " + playerStat.getPlayerName() + " with " + playerStat.getStep() + " steps";
            }
            else if(playerStat.getStep() < steps && playerStat.getEndOfGameResult().equals("WON")){
                steps = playerStat.getStep();
                playerName = playerStat.getPlayerName();
                result = "Success in minimum steps: " + playerName + " with " + steps + " steps";
            }
        }
        return result;
    }

    /**
     * Get method for endOfGameResult
     * @return The String contains the end of game result
     * @author Yida Chen
     */
    private String getEndOfGameResult() {
        return endOfGameResult;
    }

    /**
     * Get method for finishTime
     * @return The double value represent the player`s finished time
     * @author Yida Chen
     */
    private double getFinishTime() {
        return finishTime;
    }

    /**
     * Get method for step
     * @return The steps the player used to finish the game.
     */
    private int getStep() {
        return step;
    }

    /**
     * Get method for playerName
     * @return The name of the player
     * @author Yida Chen
     */
    private String getPlayerName() {
        return playerName;
    }
}