//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.client;

/* The client package should contain all code and classes needed to run the Client
 */

/* The CAOClient offers students a menu and sends messages to the server using TCP Sockets
 */

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.*;

import com.dkit.gd2.kevinmcenroe.core.CAOService;
import com.dkit.gd2.kevinmcenroe.core.Colours;
import com.dkit.gd2.kevinmcenroe.core.StudentDTO;

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
        RequestGenerator requestGenerator = new RequestGenerator();

        while(loop)
        {
            MenuManager.displayMainMenu();
            try{
                Socket dataSocket = new Socket(CAOService.HOSTNAME, CAOService.PORT_NUM);
                OutputStream out = dataSocket.getOutputStream();
                PrintWriter output = new PrintWriter(new OutputStreamWriter(out), true);
                InputStream in = dataSocket.getInputStream();
                Scanner scannerInput = new Scanner(new InputStreamReader(in));
                Scanner keyboard = new Scanner(System.in);

                String message;
                String response = "";
                String input = keyboard.nextLine();

                if (input.length() != 1)
                    throw new IllegalArgumentException();
                else
                    option = Integer.parseInt(input);

                if (option < 0 || option >= StartMenu.values().length)
                    throw new IllegalArgumentException();

                StartMenu menuOption = StartMenu.values()[option];
                switch (menuOption) {
                    case QUIT_APPLICATION:
                        loop = false;
                        break;
                    case REGISTER:
                        StudentDTO studentToRegister = menuManager.displayStudentMenu();
                        message = requestGenerator.generateRegisterRequest(studentToRegister);
                        serverSendAndReceive(message, scannerInput, output);
                        break;
                    case LOGIN:
                        StudentDTO studentToLogIn = menuManager.displayStudentMenu();
                        message = requestGenerator.generateLogInRequest(studentToLogIn);
                        response = serverSendAndReceive(message, scannerInput, output);

                        if (response.equals(CAOService.SUCCESSFUL_LOGIN))
                            doLoggedInMenuLoop(studentToLogIn.getCaoNumber());
                        else
                            System.out.println(Colours.RED + "Invalid log in details" + Colours.RESET);

                        break;
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
            } catch(ConnectException ce)
            {
                System.out.println(Colours.RED + "Failed to connect to the server. Please ensure the server is running" + Colours.RESET);
                loop = false;
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            catch(NoSuchElementException nse)
            {
                //This exception can occur if the mysql/xampp is not running
                System.out.println(Colours.RED + "doMainMenuLoop - " + nse.getMessage() + "\nPlease ensure MySQL is running" + Colours.RESET);
            }
        }
        System.out.println("Thanks for using the app");
    }

    private void doLoggedInMenuLoop(int loggedInCAONumber)
    {
        boolean loop = true;
        int option;
        MenuManager menuManager = new MenuManager();
        RequestGenerator requestGenerator = new RequestGenerator();

        while(loop)
        {
            MenuManager.displayLoggedInMenu();
            try
            {
                Socket dataSocket = new Socket(CAOService.HOSTNAME, CAOService.PORT_NUM);
                OutputStream out = dataSocket.getOutputStream();
                PrintWriter output = new PrintWriter(new OutputStreamWriter(out), true);
                InputStream in = dataSocket.getInputStream();
                Scanner scannerInput = new Scanner(new InputStreamReader(in));
                Scanner keyboard = new Scanner(System.in);

                String message;
                String response = "";
                String input = keyboard.nextLine();

                if(input.length() != 1)
                    throw new IllegalArgumentException();
                else
                    option = Integer.parseInt(input);

                if(option < 0 || option >= LoggedInMenu.values().length)
                    throw new IllegalArgumentException();

                LoggedInMenu menuOption = LoggedInMenu.values()[option];
                switch (menuOption){
                    case QUIT:
                        loop = false;
                        break;
                    case LOGOUT:
                        System.out.println("\nLogging out...");
                        message = requestGenerator.generateLogOutRequest();
                        serverSendAndReceive(message, scannerInput, output);

                        loggedInCAONumber = -1;
                        System.out.println(Colours.GREEN + "Logged out" + Colours.RESET);
                        loop = false;
                        break;
                    case DISPLAY_COURSE:
                        String courseID = menuManager.displayGetCourseMenu();
                        message = requestGenerator.generateCourseRequest(courseID);
                        response = serverSendAndReceive(message, scannerInput, output);

                        menuManager.displayParsedCourse(response);
                        break;
                    case DISPLAY_ALL_COURSES:
                        message = requestGenerator.generateAllCoursesRequest();
                        response = serverSendAndReceive(message, scannerInput, output);

                        menuManager.displayParsedAllCourses(response);
                        break;
                    case DISPLAY_CURRENT_CHOICES:
                        message = requestGenerator.generateCurrentChoicesRequest(loggedInCAONumber);
                        response = serverSendAndReceive(message, scannerInput, output);

                        menuManager.displayParsedCurrentChoices(response);
                        break;
                    case UPDATE_CURRENT_CHOICES:
                        //Get all courses for later input validation
                        System.out.println("Retrieving up-to-date course list from the server for update validation..");
                        message = requestGenerator.generateAllCoursesRequest();
                        response = serverSendAndReceive(message, scannerInput, output);
                        List<String> allCourses = menuManager.displayParsedAllCourses(response);

                        List<String> newChoices = menuManager.displayUpdateCourseChoicesMenu(loggedInCAONumber, allCourses);

                        if(newChoices != null) {
                            message = requestGenerator.generateUpdateChoicesRequest(loggedInCAONumber, newChoices);
                            serverSendAndReceive(message, scannerInput, output);

                            System.out.println("Submitted choices...");
                            for(int subChoiceIndex = 0; subChoiceIndex<newChoices.size(); subChoiceIndex++)
                                System.out.print(Colours.GREEN + "[#"+ (subChoiceIndex+1) +"] "+ newChoices.get(subChoiceIndex) + " " + Colours.RESET);
                            System.out.println();
                        }
                        break;
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
            catch (IOException e)
            {
                System.out.println(Colours.RED + "Please enter a valid option (IOException - " + e.getMessage() + ")" + Colours.RESET);
            }
        }
    }


    private String serverSendAndReceive(String message, Scanner scannerInput, PrintWriter output){
        //Send message and listen for response
        output.println(message);
        String response = scannerInput.nextLine();
        System.out.println("Sent: " + Colours.YELLOW + message + Colours.RESET);
        System.out.println("Response: " + Colours.YELLOW + response + Colours.RESET);
        return response;
    }


}
