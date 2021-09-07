/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Yida Chen
 * Section: CSCI 205 9:00 AM
 * Date: 10/9/19
 * Time: 9:56 PM
 *
 * Project: csci205_hw
 * Package: mastermind
 * Class: HostPlayer
 *
 * Description:
 *
 * ****************************************
 */
package hw01.net;

import hw01.GameUI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


/**
 * Host server class represent the server establish by host player.
 * Multiple users can connect to a single server, so that they can play together
 * In each game, there will be one protocol with the same game,
 * so that all the player make guess of the same sequence of order
 * the protocol a server contains will be reset, when starting a new game.
 * @author Yida Chen, Jacky Lin
 */
public class HostServer {
    /** The port number of the server */
    private int portNumber;
    /** The socket of the server */
    private ServerSocket serverSocket;
    /** The protocol of the server */
    private MastermindProtocol protocol;
    /** the number of players*/
    private int numPlayers;

    /**
     * the constructor of the server
     * @param portNumber the port number of the server
     * @author Yida Chen, Jacky Lin
     */
    public HostServer(int portNumber, int numPlayers){
        this.portNumber = portNumber;
        this.numPlayers = numPlayers;
        this.protocol = new MastermindProtocol(this.numPlayers);
    }

    /**
     * this method is to establish the connection and initialized the serverSocket
     * if port number is invalid, it will allow user to re-enter another port number
     * @see<a href="https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockServer.java">
     *     https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockServer.java</a>
     * @author Jacky Lin
     */
    public void establishServer(){
        boolean connected = false;
        while (!connected){
            try {
                serverSocket = new ServerSocket(portNumber);
                System.out.println("Server established Connection to the Server. Port Number: " + portNumber);
                connected = true;
            }
            catch (IOException e){
                System.out.print("Cannot listen to the port " + portNumber + "\nEnter a new port to connect to the server:");
                Scanner in = new Scanner(System.in);
                portNumber = in.nextInt();
                setPortNumber(portNumber);
                GameUI.setPortNum(portNumber);
            }
        }
    }

    /**
     * this method is used to accept socket and handle the exception
     * @see <a href="https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockServer.java">
     *     https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockServer.java</a>
     * @return the socket the server received or null if there is an exception
     * @author Yida Chen
     */
    public Socket acceptConnection(){
        try{
            return this.serverSocket.accept();
        }
        catch (IOException e){
            System.out.println("IOException when try to accept the connection. Accept failed.");
        }
        return null;
    }

    /**
     * getter method for the protocol
     * @return the protocol
     * @author Yida Chen
     */
    public MastermindProtocol getProtocol() {
        return protocol;
    }

    /**
     * method to close the server and handle the IOException when closing it
     * @author Yida Chen
     */
    public void closeServer(){
        try {
            this.serverSocket.close();
        }
        catch (IOException e){
            System.out.println("IOException when close the server, closing failed.");
        }
    }

    /**
     * setter method for the port number
     * @param portNumber the port number to set
     * @author Yida Chen
     */
    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    /**
     * this method is used to reset the protocol when restart game
     * @author Jacky Lin
     */
    public void resetProtocol() {
        this.protocol  = new MastermindProtocol(this.numPlayers);
    }
}
