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

public class CAOServer {
    public static void main(String[] args) {
        try {
            //Step 1: Set up a listening socket - this just listens for connections
            //Once a socket connects we create a dataSocket for the rest of the
            //communication
            ServerSocket listeningSocket = new ServerSocket(CAOService.PORT_NUM);
            Socket dataSocket = new Socket();

            boolean continueRunning = true;

            while (continueRunning) {
                //Step 2
                //Once a connection is accepted on the listening socket
                //spawn a dataSocket for the rest of the communication
                dataSocket = listeningSocket.accept();

                //Step 3 - set up input and output streams
                OutputStream out = dataSocket.getOutputStream();
                //Decorator pattern
                PrintWriter output = new PrintWriter(new OutputStreamWriter(out));

                InputStream in = dataSocket.getInputStream();
                Scanner input = new Scanner(new InputStreamReader(in));

                String incomingMessage = "";
                String response;

                while (true) {

                    response = null;

                    //Take the information from the client
                    incomingMessage = input.nextLine();
                    System.out.println("Received message: " + incomingMessage);

                    String[] messageComponents = incomingMessage.split(CAOService.BREAKING_CHARACTER);
                    //REGISTER%%$caoNumber%%$dateOfBirth%%$password
                    //REGISTERED if successful or REG FAILED if unsuccessful
                    if (messageComponents[0].equals(CAOService.REGISTER_COMMAND))
                    {
                        StringBuffer echoMessage = new StringBuffer("");
                        MySqlStudentDAO studentDAO = new MySqlStudentDAO();

                        int caoNumber = Integer.parseInt(messageComponents[1]);
                        String dateOfBirth = messageComponents[2];
                        String password = messageComponents[3];

                        Student student = new Student(caoNumber, dateOfBirth, password);

                        try{
                            if(studentDAO.isRegistered(student.getCaoNumber())) {
                                //Student of that CAO number already exists
                                echoMessage.append(Colours.RED + "REG FAILED");
                            }
                            else
                            {
                                studentDAO.registerStudent(student);
                                if(studentDAO.isRegistered(student.getCaoNumber()))
                                {
                                    echoMessage.append(Colours.GREEN + "REGISTERED");
                                }
                                else
                                {
                                    echoMessage.append(Colours.RED + "REG FAILED");
                                }
                            }
                        }
                        catch (DAOException throwables)
                        {
                            throwables.printStackTrace();
                        }

                        echoMessage.append(Colours.RESET);
                        response = echoMessage.toString();
                    }

                    /*
                    if (messageComponents[0].equals(CAOService.REGISTER_COMMAND))
                    {
                        StringBuffer echoMessage = new StringBuffer("");

                        echoMessage.append(CAOService.REGISTER_COMMAND);
                        echoMessage.append(CAOService.BREAKING_CHARACTER);

                        if (messageComponents.length > 1) {
                            echoMessage.append(messageComponents[1]);
                        }
                        for (int i = 2; i < messageComponents.length; i++) {
                            echoMessage.append(CAOService.BREAKING_CHARACTER);
                            echoMessage.append(messageComponents[i]);
                        }
                        response = echoMessage.toString();
                    }
                    /*else if (messageComponents[0].equals(CAOService.DAYTIME))
                    {
                        response = new Date().toString();
                    }
                    else if (messageComponents[0].equals(CAOService.STATS))
                    {
                        response = "This has not been implemented yet. This is for homework";
                    }
                    else if (messageComponents[0].equals(CAOService.END_SESSION))
                    {
                        response = CAOService.SESSION_TERMINATED;
                    }
                    else
                    {
                        response = CAOService.UNRECOGNISED;
                    }*/

                    //Send response back
                    output.println(response);
                    output.flush();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Shutting down. I think we need threads");
            System.exit(1);
        }
    }
}

