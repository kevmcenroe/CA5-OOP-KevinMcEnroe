//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

/*
The CAOClientHandler will run as a thread. It should listen for messages from the Client and respond to them.There should be one CAOClientHandler per Client.
 */

import com.dkit.gd2.kevinmcenroe.core.CAOService;
import com.dkit.gd2.kevinmcenroe.core.Colours;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CAOClientHandler implements Runnable
{
    private final Socket clientSocket;
    private final DAODriver daoDriver;

    public CAOClientHandler(Socket clientSocket, DAODriver daoDriver)
    {
        this.clientSocket = clientSocket;
        this.daoDriver = daoDriver;
    }

    @Override
    public void run()
    {
        try
        {
            Scanner clientInput = new Scanner(clientSocket.getInputStream());
            PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);

            boolean sessionActive = true;
            System.out.println("Waiting for client input");
            while(sessionActive)
            {
                //NextLine() is blocking so the program execution waits there until it receives a request

                String request = clientInput.nextLine();

                System.out.println("Received: " +Colours.YELLOW + request + Colours.RESET);
                String[] components = request.split(CAOService.BREAKING_CHARACTER);
                String response;

                ICommand command = CommandFactory.createCommand(components[0]);

                if (command != null)
                {
                    response = command.generateResponse(components, daoDriver);
                    if (response != null)
                    {
                        clientOutput.println(response);
                        System.out.println("Sent: " +Colours.YELLOW + response + Colours.RESET);
                    }
                }
            }

            clientSocket.close();
        }
        catch (IOException e)
        {
            System.out.println("Problem setting up communication channels " + e.getMessage());
        }
    }

}

