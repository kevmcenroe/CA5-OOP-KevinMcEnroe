//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.client;

/* The client package should contain all code and classes needed to run the Client
 */

/* The CAOClient offers students a menu and sends messages to the server using TCP Sockets
 */

import java.io.*;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.dkit.gd2.kevinmcenroe.core.CAOService;
import com.dkit.gd2.kevinmcenroe.core.Colours;
import com.dkit.gd2.kevinmcenroe.core.Student;

public class CAOClient
{
    public static Scanner keyboard = new Scanner(System.in);

    public static void main( String[] args )
    {
        System.out.println( "CAO Online - CA5" );
        new CAOClient().start();
    }

    private void start() {
        doMainMenuLoop();
    }

    //Adapted from my CA4 submission
    private void doMainMenuLoop()
    {
        boolean loop = true;
        int option;
        MenuManager menuManager = new MenuManager();

        while(loop)
        {
            printMainMenu();
            try
            {
                /* This code will be revisited in Deliverable 2
                Socket dataSocket = new Socket(CAOService.HOSTNAME, CAOService.PORT_NUM);

                OutputStream out = dataSocket.getOutputStream();
                PrintWriter output = new PrintWriter(new OutputStreamWriter(out));
                InputStream in = dataSocket.getInputStream();
                Scanner scannerInput = new Scanner(new InputStreamReader(in));
                Scanner keyboard = new Scanner(System.in);
                */

                DAODriver daoDriver = new DAODriver();
                
                String message = "";
                String input = keyboard.nextLine();

                while(!message.equals(CAOService.LOGOUT_COMMAND)) {
                    if (input.length() != 1)
                        throw new IllegalArgumentException();
                    else
                        option = Integer.parseInt(input);

                    if (option < 0 || option >= StartMenu.values().length)
                        throw new IllegalArgumentException();
                    //String response = "";

                    StartMenu menuOption = StartMenu.values()[option];
                    switch (menuOption) {
                        case QUIT_APPLICATION:
                            loop = false;
                            break; // Exit the loop
                        case REGISTER:
                            Student student = menuManager.displayRegisterStudentMenu();
                            message = generateRegisterRequest(student);
                            System.out.println("Generated: " + Colours.GREEN + message + Colours.RESET);

                            daoDriver.registerStudent(student);

                            doMainMenuLoop();
                            break;
                            /* This code will be revisited in Deliverable 2
                            //Send message
                            output.println(message);
                            output.flush();

                            //Listen for response
                            response = scannerInput.nextLine();
                            System.out.println("Sent: " + message);
                            System.out.println("Response: " + response);
                            */
                        case LOGIN:
                            menuManager.displayLogInStudent();
                            break;
                    }
                }
            }
            catch(InputMismatchException ime)
            {
                System.out.println(Colours.RED + "Please enter a valid option" + Colours.RESET);
                keyboard.nextLine();
            }
            catch(IllegalArgumentException iae)
            {
                System.out.println(Colours.RED + "Please enter a valid option" + Colours.RESET);
            }
        }
        System.out.println("Thanks for using the app");
    }

    private String generateRegisterRequest(Student student){
        StringBuffer message = new StringBuffer(CAOService.REGISTER_COMMAND);
        message.append(CAOService.BREAKING_CHARACTER);

        String caoNumber = Integer.toString(student.getCaoNumber());
        String dateOfBirth = student.getDayOfBirth();
        String password = student.getPassword();

        message.append(caoNumber);
        message.append(CAOService.BREAKING_CHARACTER);

        message.append(dateOfBirth);
        message.append(CAOService.BREAKING_CHARACTER);

        message.append(password);
        message.append(CAOService.BREAKING_CHARACTER);

        return message.toString();
    }

    /*Potential refactoring
    message = generateRequest(StartMenu.REGISTER, student);
    private String generateRequest(StartMenu option, Student student){
        switch (option) {
            case REGISTER:
                StringBuffer message = new StringBuffer(CAOService.REGISTER_COMMAND);
                message.append(CAOService.BREAKING_CHARACTER);

                String caoNumber = Integer.toString(student.getCaoNumber());
                String dateOfBirth = student.getDayOfBirth();
                String password = student.getPassword();

                message.append(caoNumber);
                message.append(CAOService.BREAKING_CHARACTER);
                message.append(dateOfBirth);
                message.append(CAOService.BREAKING_CHARACTER);
                message.append(password);
                message.append(CAOService.BREAKING_CHARACTER);

                return message.toString();
        }
    }
 */

    private void doLogInMenu(){

    }

    //Adapted from my CA4 submission
    private void printMainMenu()
    {
        System.out.println("\nMenu Options:");
        for(int i=0; i < StartMenu.values().length; i++)
        {
            String menuOption = StartMenu.values()[i].toString().replaceAll("_", " ");
            System.out.println("\t" + Colours.BLUE + i + ". " + menuOption + Colours.RESET);
        }
        System.out.println("Enter the corresponding number to select an option");
    }

    private void doLoggedInMenuLoop()
    {
        boolean loop = true;
        int option;
        while(loop)
        {
            printLoggedInMenu();
            try
            {
                String input = keyboard.nextLine();
                if(input.length() != 1)
                    throw new IllegalArgumentException();
                else
                    option = Integer.parseInt(input);

                if(option < 0 || option >= LoggedInMenu.values().length)
                    throw new IllegalArgumentException();

                //courseChoicesManager.syncCourseData();
                LoggedInMenu menuOption = LoggedInMenu.values()[option];
                switch (menuOption)
                {
                    case QUIT:
                        //courseChoicesManager.displayCourseDetails();
                        break; // exit the loop
                    case LOGOUT:
                        //courseChoicesManager.displayAllCourses();
                        break;
                    case DISPLAY_COURSE:
                        //courseChoicesManager.displayStudentChoices(student.getCaoNumber());
                        break;
                    case DISPLAY_ALL_COURSES:
                        //courseChoicesManager.displayUpdateChoices(student.getCaoNumber());
                        break;
                    case UPDATE_ALL_COURSES:
                        loop = false;
                        break; // Exit the loop
                }
            }
            catch(InputMismatchException ime)
            {
                System.out.println(Colours.RED + "Please enter a valid option (InputMismatchException - " + ime.getMessage() + ")" + Colours.RESET);
                keyboard.nextLine();
            }
            catch(IllegalArgumentException iae)
            {
                System.out.println(Colours.RED + "Please enter a valid option (IllegalArgumentException - " + iae.getMessage() + ")" + Colours.RESET);
            }
        }
        System.out.println("Thanks for using the student menu");
    }

    //Adapted from my CA3 submission
    private void printLoggedInMenu()
    {
        System.out.println("\nMenu Options:");
        for(int i = 0; i < LoggedInMenu.values().length; i++)
        {
            String menuOption = LoggedInMenu.values()[i].toString().replaceAll("_", " ");
            System.out.println("\t" + Colours.BLUE + i + ". " + menuOption + Colours.RESET);
        }
        System.out.println("Enter the corresponding number to select an option");
    }

    private void requestInput(){

    }
}
