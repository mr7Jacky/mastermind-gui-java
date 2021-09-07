/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Yida Chen
 * Section: CSCI 205 9:00 AM
 * Date: 10/13/19
 * Time: 4:15 PM
 *
 * Project: csci205_hw
 * Package: hw01.net
 * Class: GameUtility
 *
 * Description:
 *
 * ****************************************
 */
package hw01.net;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class contains helper methods to receive and check the input from the users
 * @author Yida Chen, Jacky Lin
 */
public final class GameUtility {

    /**
     * make a judgement of whether to running game in single mode
     * or running game in multiple player mode
     * @param in Scanner object allows user to input String
     * @return true if play in single mode, false if play in multi-player mode
     * @author Yida Chen
     */
    public static boolean isSingleMode(Scanner in) {
        System.out.println("Which mode of mastermind do you want to play? : ");

        while (true){
            System.out.print("Enter S/s for single player mode, or Enter M/m for multi-player mode. : ");
            String answer = in.next();
            if (answer.equalsIgnoreCase("S")){
                return true;
            }
            else if (answer.equalsIgnoreCase("M")){
                return false;
            }
            else {
                System.out.println("Invalid Answers, please try again");
            }
        }
    }

    /**
     * make a judgement of whether to host game or not
     * @param in Scanner object allows user to input String
     * @return true if host name, false if join others' game
     * @author Yida Chen
     */
     public static boolean hostingGame(Scanner in) {
        System.out.println("Do you want to host the game?");

        while (true) {
            System.out.print("Enter Y for hosting, N for joining the others` game. : ");
            String answer = in.next();
            if (answer.equalsIgnoreCase("Y")){
                return true;
            }
            else if(answer.equalsIgnoreCase("N")){
                return false;
            }
            else {
                System.out.println("Invalid Answers, please try again.");
                in.nextLine();
            }
        }
    }

    /**
     * It will check if the port number is usable. If it is not, it will complain
     * It will also allow user to re-enter a new port number, until it is usable
     * it have a default port number of 6666
     * @param in Scanner object allows user to input String
     * @return the safe port number to use
     * @author Jacky Lin
     */
    public static int getPortNum(Scanner in) {
        boolean receivedAnswer = false;
        int portNum =  6666;
        while (!receivedAnswer){
            try{
                System.out.print("Enter the portNum for the Server: ");
                portNum = in.nextInt();
                if (portNum > 40000 || portNum <= 1024) {
                    throw new IllegalArgumentException();
                }
                receivedAnswer = true;
            } catch (InputMismatchException | IllegalArgumentException e){
                System.out.println("Invalid Port Number! Please try again!");
                in.nextLine();
            }
        }
        return portNum;
    }

    /**
     * it allows users to input the number of player play together,
     * the default player number is 2
     * @param in Scanner object allows user to input String
     * @return the number of player in one game
     * @author Yida Chen
     */
    public static int getNumPlayers(Scanner in) {
        boolean receivedAnswer = false;
        int numPlayers = 2;
        while(!receivedAnswer) {
            try {
                System.out.print("How many players will join the game? : ");
                numPlayers = in.nextInt();
                receivedAnswer = true;
            } catch (InputMismatchException e){
                System.out.println("Invalid Answers, please try again.");
                in.nextLine();
            }
        }
        return numPlayers;
    }

    /**
     * This method create a {@link ClientPlayer} with name, port number and hostname
     * @param hostname the host name of the Cloent
     * @param portNum the port number of the Client
     * @param in Scanner object allows user to input String
     * @return a ClientPlayer object used for connection among users and server
     * @author Yida Chen, Jacky Lin
     */
    public static ClientPlayer getClientPlayer(String hostname, int portNum, Scanner in) {
        System.out.print("Enter the your name/nickname: ");
        String playerName = in.next();
        ClientPlayer player;
        player = new ClientPlayer(portNum, hostname, playerName);
        player.connectToServer();
        return player;
    }

    /**
     * Ask the users whether they want to run the solver mode or the game mode of the mastermind.
     * @param in Scanner read in the input from the users.
     * @return true if the user want to run the solver mode, or false if they want to run the game
     * mode.
     * @author Yida Chen
     */
    public static boolean isSolverMode(Scanner in) {
        while (true){
            System.out.print("Which mode of the Mastermind do you want to play? Enter S/s for Solver mode, G/g for Game mode: ");
            String ans = in.next();
            if (ans.equalsIgnoreCase("S")){
                return true;
            }
            else if(ans.equalsIgnoreCase("G")){
                return false;
            }
            System.out.println("Invalid Answers, please try again.");
        }
    }

    /**
     * Ask the users how many times do they want to simulate tha game with the solver.
     * @param in Scanner read in the input from the users
     * @return An int of how many times of simulation the users want to run
     * @author Yida Chen
     */
    public static int getNumTries(Scanner in) {
        int tries = 0;
        boolean validAns = false;
        while (!validAns){
            try{
                System.out.print("How many times do you want the solver to simulate the game: ");
                tries = in.nextInt();
                validAns = true;
            }
            catch (InputMismatchException e){
                System.out.println("Invalid number, please try agsin.");
                in.nextLine();
            }
        }
        return tries;
    }
}
