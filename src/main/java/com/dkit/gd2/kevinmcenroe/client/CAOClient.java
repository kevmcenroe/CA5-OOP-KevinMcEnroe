//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.client;

/* The client package should contain all code and classes needed to run the Client
 */

/* The CAOClient offers students a menu and sends messages to the server using TCP Sockets
 */

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.dkit.gd2.kevinmcenroe.core.CAOService;
import com.dkit.gd2.kevinmcenroe.core.Colours;
import com.dkit.gd2.kevinmcenroe.core.Course;
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
            MenuManager.displayMainMenu();
            try{
                DAODriver daoDriver = new DAODriver();

                String message;


                //while(!message.equals(CAOService.LOGOUT_COMMAND)) {
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
                            break; // Exit the loop
                        case REGISTER:
                            Student studentToRegister = menuManager.displayStudentMenu();
                            message = generateRegisterRequest(studentToRegister);
                            System.out.println("Generated: " + Colours.GREEN + message + Colours.RESET);

                            daoDriver.registerStudent(studentToRegister);

                            doMainMenuLoop();
                            break;
                        case LOGIN:
                            Student studentToLogIn = menuManager.displayStudentMenu();
                            message = generateLogInRequest(studentToLogIn);
                            System.out.println("Generated: " + Colours.GREEN + message + Colours.RESET);

                            if(daoDriver.logIn(studentToLogIn))
                                //Successful log in
                                doLoggedInMenuLoop(studentToLogIn.getCaoNumber());
                            else
                                doMainMenuLoop();

                            loop = false;
                            break;
                    }
                //}
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
                DAODriver daoDriver = new DAODriver();

                String message;

                String input = keyboard.nextLine();
                if(input.length() != 1)
                    throw new IllegalArgumentException();
                else
                    option = Integer.parseInt(input);

                if(option < 0 || option >= LoggedInMenu.values().length)
                    throw new IllegalArgumentException();

                LoggedInMenu menuOption = LoggedInMenu.values()[option];
                switch (menuOption)
                {
                    case QUIT:
                        loop = false;
                        break;
                    case LOGOUT:
                        message = generateLogOutRequest();
                        System.out.println("Generated: " + Colours.GREEN + message + Colours.RESET);
                        System.out.println("\nLogging out...");
                        loggedInCAONumber = -1;
                        System.out.println(Colours.GREEN + "Logged out" + Colours.RESET);
                        doMainMenuLoop();
                        break;
                    case DISPLAY_COURSE:
                        String courseID = menuManager.displayGetCourseMenu();

                        message = generateCourseRequest(courseID);
                        System.out.println("Generated: " + Colours.GREEN + message + Colours.RESET);

                        Course course = daoDriver.getCourseByCourseID(courseID);
                        if(course != null) {
                            System.out.println(Colours.GREEN + course + Colours.RESET);
                        }
                        break;
                    case DISPLAY_ALL_COURSES:
                        message = generateAllCoursesRequest();
                        System.out.println("Generated: " + Colours.GREEN + message + Colours.RESET);

                        List<Course> allCourses = daoDriver.getAllCourses();
                        if(allCourses != null){
                            for(Course foundCourse : allCourses)
                                System.out.println(Colours.GREEN + foundCourse + Colours.RESET);
                        }
                        break;
                    case DISPLAY_CURRENT_CHOICES:
                        message = generateCurrentChoicesRequest(loggedInCAONumber);
                        System.out.println("Generated: " + Colours.GREEN + message + Colours.RESET);

                        List<String> allChoices = daoDriver.getCourseChoices(loggedInCAONumber);
                        menuManager.displayCurrentChoices(loggedInCAONumber);
                        break;
                    case UPDATE_CURRENT_CHOICES:
                        List<String> newChoices = menuManager.displayUpdateCourseChoicesMenu(loggedInCAONumber);

                        if(newChoices != null) {
                            message = generateUpdateChoicesRequest(loggedInCAONumber, newChoices);
                            System.out.println("Generated: " + Colours.GREEN + message + Colours.RESET);
                            daoDriver.updateCourseChoices(loggedInCAONumber, newChoices);
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
            }
        }
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
        StringBuilder message = new StringBuilder(CAOService.LOGOUT_COMMAND);
        return message.toString();
    }

    private String generateCourseRequest(String courseID){
        StringBuilder message = new StringBuilder(CAOService.DISPLAY_COURSE_COMMAND);
        message.append(CAOService.BREAKING_CHARACTER);

        message.append(courseID);

        return message.toString();
    }

    private String generateAllCoursesRequest(){
        StringBuilder message = new StringBuilder(CAOService.DISPLAY_ALL_COURSES_COMMAND);
        return message.toString();
    }

    //DISPLAY CURRENT%%$caoNumber
    private String generateCurrentChoicesRequest(int caoNumber){
        StringBuilder message = new StringBuilder(CAOService.UPDATE_CHOICES_COMMAND);
        message.append(CAOService.BREAKING_CHARACTER);
        message.append(caoNumber);

        return message.toString();
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


    /* May be revisited for server side of protocol in Deliverable 2
    private String generateCourseRequest(Course course){
        StringBuilder message = new StringBuilder(CAOService.DISPLAY_COURSE_COMMAND);
        message.append(CAOService.BREAKING_CHARACTER);

        String courseID = course.getCourseId();
        String level = course.getLevel();
        String title = course.getTitle();
        String institution = course.getInstitution();

        message.append(courseID);
        message.append(CAOService.BREAKING_CHARACTER);

        message.append(level);
        message.append(CAOService.BREAKING_CHARACTER);

        message.append(title);
        message.append(CAOService.BREAKING_CHARACTER);

        message.append(institution);

        return message.toString();
    }
*/
}
