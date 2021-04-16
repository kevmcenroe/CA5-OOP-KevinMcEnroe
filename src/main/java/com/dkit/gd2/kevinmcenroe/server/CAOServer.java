//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

/* The server package should contain all code to run the server. The server uses TCP sockets and thread per client.
 The server should connect to a MySql database to register clients, allow them to login and choose courses
 The server should listen for connections and once a connection is accepted it should spawn a new CAOClientHandler thread to deal with that connection. The server then returns to listening
 */

import com.dkit.gd2.kevinmcenroe.core.CAOService;
import com.dkit.gd2.kevinmcenroe.core.Colours;
import com.dkit.gd2.kevinmcenroe.core.Student;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

//During development of the Client, I found myself developing part of the server to resolve socket errors
//Upon clarification from the lecturer, I removed the socket therefore removing the need for the server in Deliverable 1, as per the brief
//This code may be revisited in Deliverable 2

//Adapted from sample code
public class CAOServer {
    public static void main(String[] args) {
        DAODriver daoDriver = new DAODriver();

        try {
            ServerSocket connectionSocket = new ServerSocket(CAOService.PORT_NUM);
            while (true) {
                Socket clientSocket = connectionSocket.accept();
                CAOClientHandler clientHandler = new CAOClientHandler(clientSocket, daoDriver);
                Thread worker = new Thread(clientHandler);
                worker.start();
            }

        } catch (IOException e) {
            System.out.println("Problem setting up the connection socket " + e.getMessage());
        }

       /* try {



            //Step 1: Set up a listening socket - this just listens for connections
            //Once a socket connects we create a dataSocket for the rest of the
            //communication
            ServerSocket listeningSocket = new ServerSocket(CAOService.PORT_NUM);
            Socket dataSocket = new Socket();
            DAODriver daoDriver = new DAODriver();

            boolean continueRunning = true;

            while (continueRunning) {
                //Step 2
                //Once a connection is accepted on the listening socket
                //spawn a dataSocket for the rest of the communication
                dataSocket = listeningSocket.accept();

                //Step 3 - set up input and output streams
                OutputStream out = dataSocket.getOutputStream();
                //Decorator pattern
                PrintWriter output = new PrintWriter(new OutputStreamWriter(out), true);
                //Instead of output.flush, we can set true above

                InputStream in = dataSocket.getInputStream();
                Scanner input = new Scanner(new InputStreamReader(in));

                String incomingMessage = "";

                while (true) {

                    String response = null;

                    //Take the information from the client
                    incomingMessage = input.nextLine();
                    System.out.println("Received message: " + incomingMessage);

                    String[] messageComponents = incomingMessage.split(CAOService.BREAKING_CHARACTER);

                    //REGISTERED if successful or REG FAILED if unsuccessful
                    if (messageComponents[0].equals(CAOService.REGISTER_COMMAND))
                        response = generateRegisterResponse(daoDriver, messageComponents);

                    //LOGGED IN if successful or LOGIN FAILED is unsuccessful
                    else if (messageComponents[0].equals(CAOService.LOGIN_COMMAND))
                        response = generateLogInResponse(daoDriver, messageComponents);


                    //Send response back
                    output.println(response);
                    //output.flush();           //Autoflush so this is no longer needed
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Shutting down. I think we need threads");
            System.exit(1);
        }

             */
    }
}