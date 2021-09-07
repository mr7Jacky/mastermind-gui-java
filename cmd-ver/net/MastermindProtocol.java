/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Yida Chen
 * Section: CSCI 205 9:00 AM
 * Date: 10/10/19
 * Time: 10:23 AM
 *
 * Project: csci205_hw
 * Package: mastermind
 * Class: MastermindProtocol
 *
 * Description:
 *
 * ****************************************
 */
package hw01.net;

import hw01.game.*;

import java.util.ArrayList;

/**
 * this class is the protocol class for the server{@link HostServer}, it contains rules for the client to run
 * each protocol contains a {@link Machine} object to play the game.
 * it will control what to response to the user according to the users' input
 * @see <a href="https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockProtocol.java">
 *     https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockProtocol.java</a>
 * @author Jacky Lin, Yida Chen
 */
public class MastermindProtocol extends MasterMind{
    public static final double NANO_TO_SECOND = 10e9;
    private int answeredPlayer;
    /** the machine to hold the game*/
    private Machine m;
    /** a list of player*/
    private ArrayList<String> playerList;
    /** the number player tfinished*/
    private int finishedPlayers;
    /** the list store the statistic information of players*/
    private ArrayList<PlayerStat> playerStats = new ArrayList<>();
    /** the number of the players join the game*/
    private int joinPlayers;
    /** the number of the player want to restart the game*/
    private long againPlayer;

    /**
     * the constructor for the protocol
     * @author Jacky Lin, Yida Chen
     */
    public MastermindProtocol (int joinPlayers) {
        // The mastermind machine
        this.m = new Machine();
        // Generate the tokens that the players will guess during the game
        m.generateTokens();
        // An ArrayList of PlayerStat objects which contain the end of game result of the player
        this.playerList = new ArrayList<>();
        // Record how many players who finished the game
        this.finishedPlayers = 0;
        // Record how many players who will join the game
        this.joinPlayers = joinPlayers;
        // Record how many players have answered whether play again questions
        this.answeredPlayer = 0;
    }

    /**
     * This method controls what message returns to users, according to their input String
     * @see <a href="https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockProtocol.java">
     *     https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockProtocol.java</a>
     * @param input Expected Input should be a String consisted of 4 numbers from 1 to 6, which represent tokens on board.
     * @return Expected Output is String contains the tokens chosen by player and its correspond pegs. eg 1111 **__
     * ANSWERS, WAIT: When user sends out "ANSWERS" request, the protocol check whether every players have finished the game and update
     * their final result. If finished the protocol output the final statistics of the game to every player, otherwise send
     * "WAIT" response to the player to let them wait for the others to finish.
     *
     * AGAIN: When user input "AGAIN", it will make judgement of whether to restart game or not.
     *
     * RESTART: If there is enough user want to restart game, it will send "RESTART" to the player.
     *
     * STOP: If one player decides not to restart, otherwise it will send "STOP" to the player.
     *
     * QUIT: if player decide to "QUIT" or "STOP" the game, it will sent "QUIT" to the player.
     * @author Yida Chen, Jacky Lin
     */
     synchronized public String runGame(String input){
        String result = "";
        try{
            // Send the correct code to the players
            if (input.equals("ANSWERS")){
                for(Token token: m.getTokens()){
                    result += token.getNum() + " ";
                }
            }
            // Record the name of the player to the playerList
            else if(input.contains("NAME:")){
                String name = input.substring(5);
                playerList.add(name);
                result = name;
            }
            // Display the name of all players to each player
            else if(input.equals("DISPLAY_PLAYERS")){
                result = "Following players joining the game:";
                int id = 1;
                for (String playerName: playerList){
                    result += " " + id + "." + playerName;
                    id++;
                }
            }
            // Update the end of game result
            else if(input.contains("UPDATE")){
                PlayerStat playerStat = parsePlayerDataFromStr(input);
                playerStats.add(playerStat);
                result = "WAIT";
                finishedPlayers++;
            }
            // Resolve player`s STAT request
            else if(input.equals("STAT")){
                if (finishedPlayers == this.joinPlayers){
                    // Everyone update the result, send back the statistics result
                    result = "STAT\n";
                    result += PlayerStat.shortTestTime(playerStats) + "\n" +
                            PlayerStat.minSteps(playerStats) + "\n" + "END_GAME";
                }
                else {
                    // Else wait for all players finish the game
                    result = "WAIT";
                }
            }
            // Record the player`s response about whether restart the game
            else if(input.contains("AGAIN")){
                result = "WAIT";
                againPlayer++;
                answeredPlayer++;
            }
            // Request for response to whether restart
            else if(input.equals("RESTART")){
                if (againPlayer == joinPlayers){
                    result = "RESTART";
                }
                else if (answeredPlayer < joinPlayers) {
                    result = "WAIT";
                }
                else {
                    result = "STOP";
                }
            }
            // Stop game
            else if(input.contains("STOP")){
                result = "QUIT";
                answeredPlayer++;
            }
            // Quit game
            else if(input.equals("QUIT")){
                result = "QUIT";
            }
            // check the tokens
            else {
                //read guesses
                Token[] inputTokens = super.readTokens(input, m.TOKEN_NUM);
                //check the guess, the check result indicates how many tokens are right
                result = m.tokenChecker(inputTokens);
            }
        } catch (InputLengthException e) {
            result = "WRONG TOKEN NUMBER! Please choose only 4 tokens!" + input;
        } catch (IllegalInputException e) {
             result = "ILLEGAL INPUT CHARACTER! Please input only numbers!";
        }
        return result;
    }

    /**
     * Parse the player end of game statistics data from player`s UPDATE request and return a
     * PlayStat object contains the player`s statistics data.
     * @param input The player`s UPDATE request which contains his/her end of game result
     * @return PlayStat object contains the player`s statistics data.
     * @author Yida Chen
     */
    public PlayerStat parsePlayerDataFromStr(String input){
        // Strip out the player name
        String name = input.substring(input.indexOf(":") + 1,input.indexOf(";"));

        input = input.substring(input.indexOf(";") + 1);
        //Strip out the end of game result: win or failed
        String endOfGameResult = input.substring(0,input.indexOf(";"));

        input = input.substring(input.indexOf(";") + 1);
        // Strip out the finish time of the player and convert it to the second from nanosecond
        double finishTime = Long.parseLong(input.substring(0,input.indexOf(";")))/ NANO_TO_SECOND;

        // Strip out the steps the players used to finish the game
        int steps = Integer.parseInt(input.substring(input.indexOf(";")+1));

        // Instantiate a PlayerStat object to store all data
        PlayerStat playerStat = new PlayerStat(name, endOfGameResult, finishTime, steps);

        return playerStat;
    }
}
