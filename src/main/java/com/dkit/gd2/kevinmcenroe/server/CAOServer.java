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
            System.out.println(Colours.RED + "Problem setting up the connection socket " + e.getMessage() + Colours.RESET);
        }
    }
}