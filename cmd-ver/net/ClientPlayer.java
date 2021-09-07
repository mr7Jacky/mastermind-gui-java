/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Yida Chen
 * Section: CSCI 205 9:00 AM
 * Date: 10/10/19
 * Time: 10:50 AM
 *
 * Project: csci205_hw
 * Package: mastermind
 * Class: clientPlayer
 *
 * Description:
 *
 * ****************************************
 */
package hw01.net;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * this class represents the client in the server as a player in the game
 * @see <a href=""
 * @author Yida Chen, Jacky Lin
 */
public class ClientPlayer {
    /** the socket object for to connect to the server*/
    private Socket socket;
    /** Buffered Reader object to read the stream from the server*/
    private BufferedReader in;
    /** To write the user input to the server*/
    private PrintWriter out;
    /** the hostname of the client*/
    private String hostname;
    /** port number of the client*/
    private int portNumber;
    /** the counter for the steps*/
    private int steps;
    /** indication of whether is connected to the server*/
    public static boolean connected = false;
    /** the name of the player*/
    private String playerName;
    /** max trials a user can have*/
    public final int MAX_TRIES = 12;

    /**
     * the constructor for the {@link ClientPlayer} class, initialize some instance variable.
     * @param portNumber port number of the server
     * @param hostname host name of the server
     * @param playerName the name of the player
     * @author Jacky Lin, Yida Chen
     */
    public ClientPlayer(Integer portNumber, String hostname, String playerName){
        this.portNumber = portNumber;
        this.hostname = hostname;
        this.steps = 1;
        this.playerName = playerName;
    }

    /**
     * set the port number of the client
     * @param portNumber the port number to set
     * @author Yida Chen
     */
    public void setPortNumber(Integer portNumber) {
        this.portNumber = portNumber;
    }

    /**
     * this method is use to connected to the server. It will ask the user to input the port number to the server.
     * If establishing the connections to server failed, it will ask the user to retype a hostname or a port number,
     * until it connects to the server.
     * @see <a href="https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockClient.java">
     *     https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockClient.java</a>
     * @author Yida Chen, Jacky Lin
     */
    public void connectToServer() {
        while (!connected) {
            try {
                // Try to connect to the server
                this.socket = new Socket(hostname, portNumber);
                // Successfully connect to the server
                System.out.println("Client established Connection to the Server. Port Number: " + portNumber);
                // Wait for the other players
                System.out.println("Wait for the other players to join the game ......");
                // Get the output and input stream of the client socket
                this.out = new PrintWriter(socket.getOutputStream(), true);
                this.in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                // Break the loop
                connected = true;
            } catch (UnknownHostException e) {
                // Unknown host. Re-type hostname
                System.out.println("Unknown host: " + hostname + "");
                System.out.print("Enter the new hostname: ");
                Scanner in = new Scanner(System.in);
                setHostname(in.next());
            } catch (IOException e) {
                // unavailable port re-type port number
                System.out.print("Cannot listen to the port " + portNumber + "\nEnter a new port to connect to the server: ");
                Scanner in = new Scanner(System.in);
                setPortNumber(in.nextInt());
            }
        }
    }


    /**
     * Setter method for hostname. Set the hostname of the client socket used to connect to the server
     * @param hostname The hostname of the server
     * @author Yida Chen
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * This method start the mastermind game process for the player. It receives output from the server and
     * sends out the user`s input to server for processing.
     * @see <a href="https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockClient.java">
     *     https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockClient.java</a>
     * @author Yida Chen, Jacky Lin
     */
    public void startGame() {
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        try {
            // Send out the player`s name to the server
            out.println("NAME:" + playerName);
            // Wait for the server to process the input
            Thread.sleep(100);
            // Send out the request for displaying all players` name
            out.println("DISPLAY_PLAYERS");
            // Print out the welcome message from the server and the name list
            System.out.println(in.readLine() + "\n" + in.readLine() + "\n" + in.readLine());
            System.out.println("Your player name: " + in.readLine());

            // Record the time that player starts the game
            long startTime = System.nanoTime();

            //Start process the output from the server
            processServerOutput(userIn, startTime);

            System.out.println("Wait for the other players to finish");

            // Request for end of game result
            getEndOfGameResult();
            System.out.println("____________________________________\nGame Finished. Disconnect to the server");
        }
        catch (IOException e){
            System.out.println("Fail to read in the input.");
        }
        catch (InterruptedException e){
            System.out.println("Interrupted Exception.");
        }
    }

    /**
     * Keep sending request for end of game result to the server to get the final
     * statistics of all players and display them on the player`s screen
     * @author Yida Chen
     */
    private void getEndOfGameResult() {
        try {
            String fromMastermind;
            while((fromMastermind = in.readLine()) != null){
                Thread.sleep(100);
                // If the server sends out the statistics results. Print them out
                if (fromMastermind.contains("STAT")){
                    System.out.println("End of Game Result:");
                    while ((fromMastermind = in.readLine()) != null){
                        if (fromMastermind.equals("END_GAME")){
                            break;
                        }
                        System.out.println(fromMastermind);
                    }
                    break;
                }
                // Else keep requesting for statistic results.
                else {
                    out.println("STAT");
                }
            }
        } catch (IOException e) {
            System.out.println("IOException when try to get the end of game result from the server");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException when try to get the end of game result from the server");
        }
    }

    /**
     * Process the output from the server and send out the input from the server from the player.
     * @see <a href="https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockClient.java">
     *     https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockClient.java</a>
     * @param userIn The BufferedReader object that receives the input from the player
     * @param startTime The time when the player start the game used to compute the total time
     *                  that player used to finish the game.
     * @author Yida Chen, Jacky Lin
     */
    private void processServerOutput(BufferedReader userIn, long startTime) {
        try {
            String fromMastermind;
            String fromUser;
            // Keep getting the output from the mastermind server
            while ((fromMastermind =  in.readLine()) != null){
                // Print server message
                System.out.println(" From Mastermind: "+fromMastermind);
                // Answer is correct end the game update the result
                if (fromMastermind.equals("****")){
                    long endTime = System.nanoTime();
                    long processTime = endTime - startTime;
                    out.println("UPDATE:" + playerName + ";WON;" + processTime + ";" + (--steps));
                    System.out.println("You Win");
                    break;
                }
                // Invalid answers, type your guess again
                else if(fromMastermind.equals("TOO MUCH TOKENS! Please choose only 4 tokens!")
                        || fromMastermind.equals("ILLEGAL INPUT CHARACTER! Please input only numbers!")){
                    steps -= 1;
                }
                // Out of tries, you failed. Update the result
                else if(steps > MAX_TRIES){
                    out.println("ANSWERS");
                    fromMastermind = in.readLine();
                    System.out.println("Game Over\n" + "The right answer is " + fromMastermind);

                    long endTime = System.nanoTime();
                    long processTime = endTime - startTime;
                    out.println("UPDATE:" + playerName + ";FAILED;" + processTime + ";" + steps);
                    break;
                }

                // Else, print the peg hint from the mastermind server, and ask player`s next guess.
                System.out.printf("You %dth guess: ", steps);
                fromUser = userIn.readLine();
                if (fromUser != null){
                    System.out.print("Your guess is: "+fromUser);
                    out.println(fromUser);
                    steps += 1;
                }
            }
        } catch (IOException e) {
            System.out.println("IOException when processing the server output and send the player input back to server.");
        }
    }

    /**
     * Getter method of the BufferedReader object that receives the output from the server.
     * @return BufferedReader object that receives the output from the server.
     * @author Jacky Lin
     */
    public BufferedReader getIn() {
        return in;
    }

    /**
     * Getter method of the PrintWriter object that output user`s input to the server.
     * @return PrintWriter object that output user`s input to the server.
     * @author Jacky Lin
     */
    public PrintWriter getOut() {
        return out;
    }

    /**
     * Reset the steps of the player when the same player decides to play the game again
     * @author Jacky Lin
     */
    public void resetSteps () { this.steps = 1; }
}
