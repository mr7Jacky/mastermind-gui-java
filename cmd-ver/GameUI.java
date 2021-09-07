/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Yida Chen
 * Section: CSCI 205 9:00 AM
 * Date: 10/13/19
 * Time: 9:47 AM
 *
 * Project: csci205_hw
 * Package: hw01.net
 * Class: GameUI
 *
 * Description:
 *
 * ****************************************
 */
package hw01;

import hw01.game.Machine;
import hw01.game.MasterMind;
import hw01.game.Token;
import hw01.net.ClientPlayer;
import hw01.net.GameUtility;
import hw01.net.HostServer;
import hw01.net.MultiThreadProcessor;
import hw01.solver.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * GameUI class represent the console based Game user interface
 * Two modes are available, single player mode and multiple player mode
 * No internet connection needed for single player mode.
 * Multiple player mode accept unlimited numbers of players. The multiple player
 * mode will start when all players join the game.
 * All players only need to run the main method of this class to start the game
 * @author Yida Chen, Jacky Lin
 */
public class GameUI {
    /** decide whether to restart game or not*/
    private static boolean continued;
    /** decide whether it is the first time to play*/
    private static boolean firstTime;
    /** the socket to get and send data*/
    private Socket clientSocket;
    /** port number*/
    private static int portNum;

    /**
     * The main method the GameUI
     * @author Yida Chen
     * @param args The default arguments of the main method
     */
    public static void main(String[] args){
        // Initiate the GameUI
        GameUI UI = new GameUI();
        // Initiate the Scanner
        Scanner in = new Scanner(System.in);
        // Ask the user whether they want to run the solver mode.
        boolean solverMode = GameUtility.isSolverMode(in);
        if (solverMode){
            // Run the Solver mode.
            UI.solverMode(in);
        }
        else {
            // Run the Game mode
            boolean singleMode = GameUtility.isSingleMode(in);
            continued = true;
            if (singleMode) {
                UI.singleModeMastermind(in);
            } else {
                UI.multiModeMastermind(in);
            }
        }
    }

    /**
     * Run the solver mode of the mastermind.
     * @param in Scanner that read in the input from the user
     * @author Yida Chen, Jacky Lin
     */
    private void solverMode(Scanner in){
        // Ask the users how many times do they want to simulate the game with solver
        while (true) {
            int tries = GameUtility.getNumTries(in);
            boolean receivedAns = false;
            while(!receivedAns){
                // Ask the users which mode of the solver do they want to use to simulate the game.
                System.out.print("Which Solver do you want to use to simulate the Mastermind game?\n" +
                        "Enter R/r for Random Solver. Enter M/m for Mini Max Solver. Enter C/c for CustomSolver.\n");
                String ans = in.next();
                ans = ans.toUpperCase();
                switch (ans){
                    // Run the random solver
                    case "R":
                        System.out.println("----------------------------------------\n" +
                                "Simulate with random solver for " + tries + " times.");
                        SolverSimulator.simulate(new RandomSolver(), tries);
                        receivedAns = true;
                        break;
                    // Run the minimax solver
                    case "M":
                        System.out.println("----------------------------------------\n" +
                                "Simulate with minimax solver for " + tries + " times.");
                        SolverSimulator.simulate(new MinimaxSolver(), tries);
                        receivedAns = true;
                        break;
                    // Run the custom solver
                    case "C":
                        System.out.println("----------------------------------------\n" +
                                "Simulate with customer solver for " + tries + " times.");
                        SolverSimulator.simulate(new CustomSolver(), tries);
                        receivedAns = true;
                        break;
                    default:
                        System.out.println("Invalid Answers, please try again");
                }
            }
            System.out.println("Do you want to solve again? [Y to continued] ");
            String ans = in.next();
            if (!ans.equalsIgnoreCase("Y")) {
                break;
            }
        }
    }

    /**
     * Single Player Mode version of the mastermind. Only one player in the game.
     * Offline no connection needed.
     * @param in The Object of the Scanner class receive the input from the player
     * @author Jacky Lin, Yida Chen
     */
    private static void singleModeMastermind(Scanner in) {
        while (true) {
            Machine m = new Machine();
            m.generateTokens();
            MasterMind.setCorrectAnswer(m.printToken());

            //print rules
            System.out.println("---------MASTERMIND----------");
            System.out.println("Guess my code, using numbers between 1 and 6. You have 12 guesses:");

            //game begin
            while (MasterMind.isGameRunning()){
                try{
                    //input the guessing
                    System.out.printf("Guess %s: ", MasterMind.guessCount);
                    String input = in.next();
                    //read guesses
                    Token[] inputTokens = MasterMind.readTokens(input, m.TOKEN_NUM);
                    //check the guess, the check result indicates how many tokens are right
                    String checkResult = m.tokenChecker(inputTokens);
                    //print result accordingly
                    MasterMind.displayResult(input, checkResult);
                    //Increase the number of steps the player has used by 1
                    MasterMind.guessCount++;
                } catch (MasterMind.InputLengthException e) {
                    System.out.println("WRONG TOKENS NUMBER! Please choose only 4 tokens!");
                } catch (MasterMind.IllegalInputException e) {
                    System.out.println("ILLEGAL INPUT CHARACTER! Please input only numbers from 1 to 6!");
                }
            }
            // ask user if he/she want to start again
            System.out.println("Do you want to play it again? [Y to continue]");
            String ans = in.next();
            if (!ans.equalsIgnoreCase("Y")) {
                break;
            }
            MasterMind.resetGuessCount();
            MasterMind.resetGameRunning();
        }
    }

    /**
     * The multiple player version of the mastermind. Can accept unlimited number of players.
     * Connection is needed for playing multi player mode
     * @param  in The Object of the Scanner class receive the input from the player
     * @author Yida Chen, Jacky Lin
     */
    private void multiModeMastermind(Scanner in) {
        boolean hostGame = GameUtility.hostingGame(in);
        if (hostGame){
            startAsHostPlayer(in);
        }
        else {
            startAsClientPlayer(in);
        }
    }

    /**
     * Start as the host player when play the multiple mode.
     * The host player will need to establish the server and sharing his
     * hostname with the other players when they try to connect his server
     * @param in The Object of the Scanner class receive the input from the player
     * @author Yida Chen, Jacky Lin
     */
    private void startAsHostPlayer(Scanner in) {
        //setup parameters as a host
        int numPlayers = GameUtility.getNumPlayers(in);
        ArrayList<MultiThreadProcessor> multiThreadProcessors = new ArrayList<>();
        String hostname = getHostname(in);
        portNum = GameUtility.getPortNum(in);
        HostServer server = new HostServer(portNum,numPlayers);
        server.establishServer();
        ClientPlayer player = GameUtility.getClientPlayer(hostname, portNum, in);
        ArrayList<Socket> sockets = new ArrayList<>();
        clientSocket = null;
        firstTime = true;
        // game play
        while (continued) {
            processGameOnServer(numPlayers, multiThreadProcessors, server, player,sockets);
            server.resetProtocol();
        }
        try {
            for(Socket socket: sockets){ socket.close(); }
        } catch (IOException e) { System.out.println("IOException when trying to close the socket."); }
        System.out.println("____________________________________\nDisconnect to the host server.");
        server.closeServer();
    }

    /**
     * This method is run under other method, as an assistance to help make judge whether to stop game or restart.
     * This method ask user if they want to start again. If not all user agree to play again, the game will end
     * If all the user agree to play again. game will restart, game will be reset.
     * @param player the player make decision
     * @author Jacky Lin, Yida Chen
     */
    private void playAgain(ClientPlayer player) {
        try {
            Scanner in = new Scanner(System.in);
            // ask user if he/she want to start again
            System.out.println("Do you want to play it again? [Y to continue]");
            String again = in.next();
            String userIn;
            // if user says yes
            if (again.equalsIgnoreCase("Y")) {
                player.getOut().println("AGAIN");
                System.out.println("Waiting other users to response...");
                // this loop will constantly received message
                while ((userIn = player.getIn().readLine()) != null) {
                    Thread.sleep(100);
                    if (userIn.equals("RESTART")) {
                        // if allow to restart
                        continued = true;
                        player.getOut().println("QUIT");
                        firstTime = false;
                        player.resetSteps();
                        break;
                    } else if (userIn.equals("STOP")) {
                        // if stop
                        continued = false;
                        System.out.println("Other player don't want to continue");
                        player.getOut().println("QUIT");
                        break;
                    }
                    else { player.getOut().println("RESTART"); }
                }
            // if user says other
            } else {
                player.getOut().println("STOP");
                continued = false;
            }
        } catch (IOException e) {
            System.out.println("IOException during the restart process.");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException during the restart process.");
        }
    }

    /**
     * Process the mastermind game on the server. Accepting connection requests from all players.
     * Send the confirmation message and start the game when all players join the game.
     * @see <a href="https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server">
     *     https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server</a>
     *     <a href="https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html">
     *         https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html</a>
     * @param numPlayers number of the players who will join the game.
     * @param multiThreadProcessors ArrayList contains multiThreadProcessor corresponding to each client player`s game process
     * @param server HostServer object represent the server
     * @param player ClientPlayer object represent the player
     * @param sockets ArrayList Sockest contains sockets corresponding to the clientSocket of each player
     * @author Yida Chen, Jacky Lin
     */
    private void processGameOnServer(int numPlayers, ArrayList<MultiThreadProcessor> multiThreadProcessors,
                                            HostServer server, ClientPlayer player,
                                            ArrayList<Socket> sockets){
        try {
            for (int i = 0; i < numPlayers; i++){
                if (firstTime) {
                    clientSocket = server.acceptConnection();
                    sockets.add(clientSocket);
                }
                else {
                    clientSocket = sockets.get(i);
                }
                MultiThreadProcessor multi = new MultiThreadProcessor(clientSocket, server.getProtocol());
                multiThreadProcessors.add(multi);
                if (i == (numPlayers - 1)){
                    for (int j = numPlayers - 1; j >= 0; j--){
                        multiThreadProcessors.get(j).start();
                        if (j == 0){
                            player.startGame();
                        }
                    }
                    playAgain(player);
                    for (MultiThreadProcessor thread: multiThreadProcessors){
                        thread.join();
                    }
                    multiThreadProcessors.clear();
                }
            }
        } catch (IOException e) {
            System.out.println("IOException during the game process.");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException during the game process.");
        }
    }

    /**
     * Get the hostname of the server from the user.
     * @param in
     * @return A String contains the hostname
     * @author Yida Chen
     */
    private String getHostname(Scanner in) {
        System.out.print("Enter you hostname and share it with the players who play with you: ");

        return in.next();
    }

    /**
     * Start as the client player. Wait for the host player sharing his hostname and port number
     * to connect to the server, and wait for all players connecting to the server to start the game.
     * @param in The Object of the Scanner class receive the input from the player
     * @author Yida Chen, Jacky Lin
     */
    private void startAsClientPlayer(Scanner in) {
        System.out.println("Waiting for the host to give your his host address.");
        System.out.print("Enter the address: ");
        String hostname = in.next();
        portNum = GameUtility.getPortNum(in);
        ClientPlayer player = GameUtility.getClientPlayer(hostname, portNum, in);
        while (continued) {
            player.startGame();
            // ask user if he/she want to start again
            this.playAgain(player);
        }
    }

    /**
     * Set the port number that player or server used to establish connection
     * @param portNum The port number used to establish connection
     * @author Yida Chen
     */
    public static void setPortNum(int portNum) { GameUI.portNum = portNum; }
}
