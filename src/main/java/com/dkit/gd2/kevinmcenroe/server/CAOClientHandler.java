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
import java.util.Date;
import java.util.List;
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
            while(sessionActive)
            {
                //Protocol logic
                //Note the line below is blocking - program execution waits here until we get a request
                System.out.println("Waiting for client input");
                String request = clientInput.nextLine();
                System.out.println("Received: " +Colours.GREEN + request + Colours.RESET);

                String[] components = request.split(CAOService.BREAKING_CHARACTER);
                String response;

                ICommand command = CommandFactory.createCommand(components[0]);
                System.out.println("create command called");
                if (command != null)
                {
                    response = command.generateResponse(components, daoDriver);
                    if (response != null)
                    {
                        clientOutput.println(response);
                        /*if (command instanceof ExitCommand)
                        {
                            sessionActive = false;
                        }*/
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

