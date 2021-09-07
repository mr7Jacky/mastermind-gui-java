/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Yida Chen
 * Section: CSCI 205 9:00 AM
 * Date: 10/11/19
 * Time: 9:27 PM
 *
 * Project: csci205_hw
 * Package: hw01.net
 * Class: MultiThreadProcesser
 *
 * Description:
 *
 * ****************************************
 */
package hw01.net;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * this class is designed for multiple players, every client with be a thread in the server
 * @see <a href="https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server">
 *     https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server</a>
 *     <a href="https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html">
 *         https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html</a>
 * @author Yida Chen, Jacky Lin
 */
public class MultiThreadProcessor extends Thread{
    /** the out stream to the user*/
    private PrintWriter out;
    /** reader object to obtain input from socket*/
    private BufferedReader in;
    /** protocol object for the game*/
    private MastermindProtocol protocol;

    /**
     * the constructoe of the {@link MultiThreadProcessor}
     * @see <a href="https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server">
     *     https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server</a>
     * @param clientSocket the socket to process
     * @param protocol the protocol for reference;
     * @throws IOException
     * @author Yida Chen
     */
    public MultiThreadProcessor(Socket clientSocket, MastermindProtocol protocol) throws IOException{
        //print initiation message
        System.out.println("From the server: One player joins your game.");
        // initialize the object
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.protocol = protocol;
    }

    /**
     * the obtain the user input and process them and return the message to the user
     * @see <a href="https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server">
     *     https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server</a>
     *     <a href="https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html">
     *         https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html</a>
     * @throws IOException
     * @throws InterruptedException
     * @author Yida Chen, Jacky Lin
     */
    public void listenToClient() throws IOException, InterruptedException {
        // two string for the user input and server output
        String userIn;
        String serverOut;

        //running game
        out.println("All players join the Game!\n_______________START!_______________\n" +
                "Guess my code, using numbers between 1 and 6. You have 12 guesses: ");
        while ((userIn = in.readLine()) != null){
            serverOut = protocol.runGame(userIn);
            Thread.sleep(10);
            if (serverOut.equals("QUIT")){
                break;
            }
            out.println(serverOut);
        }
    }

    /**
     * run method used for multi-thread, it will handle two exceptions thrown in listenToClient method
     * @author Yida Chen
     */
    @Override
    public void run() {
        try {
            listenToClient();
        }
        catch (IOException e){
            System.out.println("IOException during the gaming process.");
        }
        catch (InterruptedException e){
            System.out.println("Interrupted Exception");
        }
    }
}
