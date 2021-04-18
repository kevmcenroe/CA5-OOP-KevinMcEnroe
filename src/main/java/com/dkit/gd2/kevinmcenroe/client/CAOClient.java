//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.client;

/* The client package should contain all code and classes needed to run the Client
 */

/* The CAOClient offers students a menu and sends messages to the server using TCP Sockets
 */

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.dkit.gd2.kevinmcenroe.core.CAOService;
import com.dkit.gd2.kevinmcenroe.core.Colours;
import com.dkit.gd2.kevinmcenroe.core.Course;
import com.dkit.gd2.kevinmcenroe.core.Student;
//import com.dkit.gd2.kevinmcenroe.server.DAODriver;

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
            MenuManager.displayMainMenu();
            try{
                Socket dataSocket = new Socket(CAOService.HOSTNAME, CAOService.PORT_NUM);

                OutputStream out = dataSocket.getOutputStream();
                PrintWriter output = new PrintWriter(new OutputStreamWriter(out), true);

                InputStream in = dataSocket.getInputStream();
                Scanner scannerInput = new Scanner(new InputStreamReader(in));
                Scanner keyboard = new Scanner(System.in);

                String message = "";
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
                        Student studentToRegister = menuManager.displayStudentMenu();
                        message = generateRegisterRequest(studentToRegister);

                        serverSendAndReceive(message, response, scannerInput, output);
                        break;
                    case LOGIN:
                        Student studentToLogIn = menuManager.displayStudentMenu();
                        message = generateLogInRequest(studentToLogIn);

                        response = serverSendAndReceive(message, response, scannerInput, output);

                        if (response.equals(CAOService.SUCCESSFUL_LOGIN))
                            doLoggedInMenuLoop(studentToLogIn.getCaoNumber());

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

                String message = "";
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
                        message = generateLogOutRequest();
                        serverSendAndReceive(message, response, scannerInput, output);

                        loggedInCAONumber = -1;
                        System.out.println(Colours.GREEN + "Logged out" + Colours.RESET);
                        loop = false;
                        break;
                    case DISPLAY_COURSE:
                        String courseID = menuManager.displayGetCourseMenu();

                        message = generateCourseRequest(courseID);
                        //TODO It's checking database before this line (before contacting server). Fix

                        serverSendAndReceive(message, response, scannerInput, output);
                        //TODO Maybe read the response and break it up to display the course

                        /*
                        Course course = daoDriver.getCourseByCourseID(courseID);
                        if(course != null) {
                            System.out.println(Colours.GREEN + course + Colours.RESET);
                        }*/
                        break;
                    case DISPLAY_ALL_COURSES:
                        message = generateAllCoursesRequest();

                        response = serverSendAndReceive(message, response, scannerInput, output);

                        parseResponseAllCourses(response);
                        /*
                        List<Course> allCourses = daoDriver.getAllCourses();
                        if(allCourses != null){
                            for(Course foundCourse : allCourses)
                                System.out.println(Colours.GREEN + foundCourse + Colours.RESET);
                        }*/
                        break;
                    case DISPLAY_CURRENT_CHOICES:
                        message = generateCurrentChoicesRequest(loggedInCAONumber);

                        //menuManager.displayCurrentChoices(loggedInCAONumber);

                        serverSendAndReceive(message, response, scannerInput, output);
                        break;
                    case UPDATE_CURRENT_CHOICES:
                        List<String> newChoices = menuManager.displayUpdateCourseChoicesMenu(loggedInCAONumber);

                        if(newChoices != null) {
                            message = generateUpdateChoicesRequest(loggedInCAONumber, newChoices);
                            serverSendAndReceive(message, response, scannerInput, output);
                        }
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseResponseAllCourses(String response){
        String[] courseComponents = response.split(CAOService.COURSE_SEPARATOR);
        System.out.println("Displaying all courses...");

        int courseIndex = 0;
        for (String course : courseComponents) {
            String[] fields = course.split(CAOService.BREAKING_CHARACTER);
            int i = 0;
            if(courseIndex == 0)
                i++;
            String courseID = fields[i];
            String level = fields[i+1];
            String title = fields[i+2];
            String institution = fields[i+3];

            System.out.println(Colours.GREEN + "Course ID = " + courseID + ", Level = " + level + ", Title = " + title + ", Institution = " + institution + Colours.RESET);
            courseIndex++;
        }
    }

    private String serverSendAndReceive(String message, String response, Scanner scannerInput, PrintWriter output){
        //Send message and listen for response
        output.println(message);
        response = scannerInput.nextLine();
        System.out.println("Sent: " + Colours.YELLOW + message + Colours.RESET);
        System.out.println("Response: " + Colours.YELLOW + response + Colours.RESET);
        return response;
    }

    private String generateRegisterRequest(Student student){
        StringBuilder message = new StringBuilder(CAOService.REGISTER_COMMAND);
        message.append(CAOService.BREAKING_CHARACTER);

        String caoNumber = Integer.toString(student.getCaoNumber());
        String dateOfBirth = student.getDayOfBirth();
        String password = student.getPassword();

        message.append(caoNumber);
        message.append(CAOService.BREAKING_CHARACTER);

        message.append(dateOfBirth);
        message.append(CAOService.BREAKING_CHARACTER);

        message.append(password);

        return message.toString();
    }

    private String generateLogInRequest(Student student){
        StringBuilder message = new StringBuilder(CAOService.LOGIN_COMMAND);
        message.append(CAOService.BREAKING_CHARACTER);

        String caoNumber = Integer.toString(student.getCaoNumber());
        String dateOfBirth = student.getDayOfBirth();
        String password = student.getPassword();

        message.append(caoNumber);
        message.append(CAOService.BREAKING_CHARACTER);

        message.append(dateOfBirth);
        message.append(CAOService.BREAKING_CHARACTER);

        message.append(password);

        return message.toString();
    }

    private String generateLogOutRequest(){
        return CAOService.LOGOUT_COMMAND;
    }

    private String generateCourseRequest(String courseID){
        return CAOService.DISPLAY_COURSE_COMMAND + CAOService.BREAKING_CHARACTER + courseID;
    }

    private String generateAllCoursesRequest(){
        return CAOService.DISPLAY_ALL_COURSES_COMMAND;
    }

    private String generateCurrentChoicesRequest(int caoNumber){
        return CAOService.DISPLAY_CHOICES_COMMAND + CAOService.BREAKING_CHARACTER + caoNumber;
    }

    private String generateUpdateChoicesRequest(int caoNumber, List<String> newChoices){
        StringBuilder message = new StringBuilder(CAOService.UPDATE_CHOICES_COMMAND);
        message.append(CAOService.BREAKING_CHARACTER);
        message.append(caoNumber);

        if(newChoices != null)
            for(String choice : newChoices){
                message.append(CAOService.BREAKING_CHARACTER);
                message.append(choice);
            }

        return message.toString();
    }
}
