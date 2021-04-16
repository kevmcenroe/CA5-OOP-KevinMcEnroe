//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.client;

import com.dkit.gd2.kevinmcenroe.core.Colours;
import com.dkit.gd2.kevinmcenroe.core.Student;
import com.dkit.gd2.kevinmcenroe.server.DAODriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MenuManager {

    private static final Scanner keyboard = new Scanner(System.in);

    private String getInput(String request, InputType inputType) {
        System.out.print("Please enter " + request + " :>");
        RegexChecker regexChecker = new RegexChecker();

        String input = keyboard.nextLine();
        switch (inputType)
        {
            case CAO_NUMBER:
                while(!regexChecker.checkCAONumber(input)){
                    System.out.print("Please enter " + request + " :>");
                    input = keyboard.nextLine();
                }
                return input;
            case DATE_OF_BIRTH:
                while(!regexChecker.checkDateOfBirth(input)){
                    System.out.print("Please enter " + request + " :>");
                    input = keyboard.nextLine();
                }
                return input;
            case PASSWORD:
                while(!regexChecker.checkPassword(input)){
                    System.out.print("Please enter " + request + " :>");
                    input = keyboard.nextLine();
                }
                return input;
            case EXISTING_COURSE_ID:
                while(!regexChecker.checkExistingCourseID(input)){
                    System.out.print("Please enter " + request + " :>");
                    input = keyboard.nextLine();
                }
                return input;
            case ANY_COURSE_ID:
                while(!regexChecker.checkAnyCourseID(input)){
                    System.out.print("Please enter " + request + " :>");
                    input = keyboard.nextLine();
                }
                return input;
        }
        return null;
    }

    //Adapted from my CA4 submission
    public static void displayMainMenu()
    {
        System.out.println("\nMenu Options:");
        for(int i=0; i < StartMenu.values().length; i++)
        {
            //The replacement of underscores seen in my previous CAs was rendered defunct as per the brief
            //However I have included the replaceAll() call for future reference and to demonstrate my ability to adjust the menu to a more user-friendly format
            String menuOption = StartMenu.values()[i].toString().replaceAll("_", "_");
            System.out.println("\t" + Colours.BLUE + i + ". " + menuOption + Colours.RESET);
        }
        System.out.println("Enter the corresponding number to select an option");
    }

    //Adapted from my CA4 submission
    public static void displayLoggedInMenu()
    {
        System.out.println("\nMenu Options:");
        for(int i = 0; i < LoggedInMenu.values().length; i++)
        {
            String menuOption = LoggedInMenu.values()[i].toString().replaceAll("_", "_");
            System.out.println("\t" + Colours.BLUE + i + ". " + menuOption + Colours.RESET);
        }
        System.out.println("Enter the corresponding number to select an option");
    }

    public Student displayStudentMenu(){
        int caoNumber = Integer.parseInt(Objects.requireNonNull(getInput("CAO Number", InputType.CAO_NUMBER)));
        String dateOfBirth = getInput("Date of Birth in YYYY-MM-DD format", InputType.DATE_OF_BIRTH);
        String password = getInput("Password (Minimum 8 characters)", InputType.PASSWORD);

        return new Student(caoNumber, dateOfBirth, password);
    }

    public String displayGetCourseMenu(){
        return getInput("Course ID", InputType.ANY_COURSE_ID);
        // Original below checks if the given course code exists too early, preventing fulfillment of protocol fail response
        // return getInput("Course ID", InputType.COURSE_ID);
    }

    public List<String> displayUpdateCourseChoicesMenu(int caoNumber){
        List<String> newChoices = new ArrayList<>();

        displayChoiceActionMenu();

        int option;
        String input = keyboard.nextLine();
        if(input.length() != 1)
            throw new IllegalArgumentException();
        else
            option = Integer.parseInt(input);

        if(option < 0 || option >= LoggedInMenu.values().length)
            throw new IllegalArgumentException();

        DAODriver daoDriver = new DAODriver();
        ChoiceActions menuOption = ChoiceActions.values()[option];
        switch (menuOption) {
            case UPDATE_ALL_CHOICES:
                System.out.println("Enter your 10 updated course choices");

                for(int i = 1; i<11; i++){
                    if(i>1)
                        System.out.println();

                    String courseID = getInput("Course ID [Choice " + i + "]", InputType.EXISTING_COURSE_ID);
                    while(newChoices.contains(courseID))
                    {
                        System.out.println(Colours.RED + "You have already submitted course ID \"" + courseID + "\" as a choice" + Colours.RESET);
                        courseID = getInput("Course ID [Choice " + i + "]", InputType.EXISTING_COURSE_ID);
                    }
                    newChoices.add(courseID);
                }

                System.out.println("\nSubmitted choices:");
                for(int i = 0; i<newChoices.size(); i++)
                {
                    System.out.println("[Choice "+ (i+1) +"]"+ newChoices.get(i));
                }
                return  newChoices;
            case UPDATE_ONE_CHOICE:
                //I developed this option to address choice update feedback from CA4
                List<String> currentChoices = daoDriver.getCourseChoices(caoNumber);
                if (currentChoices != null) {
                    newChoices = displayChangeChoiceMenu(caoNumber);
                    return newChoices;
                }
                else
                {
                    System.out.println(Colours.RED + "Initial course choices required for this action. Please use UPDATE ALL CHOICES to submit your first preferences" + Colours.RESET);
                    return null;
                }
        }

        //Default to returning the existing choices
        return daoDriver.getCourseChoices(caoNumber);
    }

    private void displayChoiceActionMenu(){
        System.out.println("\nMenu Options:");
        for(int i = 0; i < ChoiceActions.values().length; i++)
        {
            String menuOption = ChoiceActions.values()[i].toString().replaceAll("_", "_");
            System.out.println("\t" + Colours.BLUE + i + ". " + menuOption + Colours.RESET);
        }
        System.out.println("Enter the corresponding number to select an option");
    }

    public void displayCurrentChoices(int caoNumber){
        DAODriver daoDriver = new DAODriver();
        List<String> allChoices = daoDriver.getCourseChoices(caoNumber);
        if(allChoices != null){
            for(int i = 0; i<allChoices.size(); i++)
                System.out.println("[Choice " + i + "] " + Colours.GREEN + allChoices.get(i) + Colours.RESET);
        }
    }

    private List<String> displayChangeChoiceMenu(int caoNumber) {
        //Part A - Displaying choices
        DAODriver daoDriver = new DAODriver();
        List<String> allChoices = daoDriver.getCourseChoices(caoNumber);

        System.out.println("\nCurrent Choices:");
        for(int i = 0; i < allChoices.size(); i++)
        {
            String choice = allChoices.get(i);
            System.out.println("\t" + Colours.BLUE + i + ". " + choice + Colours.RESET);
        }
        System.out.println("Select the choice you would like to update");

        //Part B - Requesting change
        int option;
        String input = keyboard.nextLine();
        if (input.length() != 1)
            throw new IllegalArgumentException();
        else
            option = Integer.parseInt(input);

        if (option < 0 || option >= 9)
            throw new IllegalArgumentException();

        System.out.println("You have selected choice " + option + ": " + Colours.BLUE + allChoices.get(option) + Colours.RESET);
        String courseID = getInput("Course ID [Choice " + option + "]", InputType.EXISTING_COURSE_ID);
        while(allChoices.contains(courseID))
        {
            System.out.println(Colours.RED + courseID + " already exists as choice " + allChoices.indexOf(courseID) + Colours.RESET);
            System.out.println("Please enter another course or overwrite choice " + allChoices.indexOf(courseID) + " to free up course " + courseID);
            System.out.println("Available Courses: " + Colours.BLUE + getUnselectedCourses(daoDriver, allChoices) + Colours.RESET);
            courseID = getInput("Course ID [Choice " + option + "]", InputType.EXISTING_COURSE_ID);
        }

        allChoices.add(option, courseID);
        allChoices.remove(option+1);
        return allChoices;
    }

    private List<String> getUnselectedCourses(DAODriver daoDriver, List<String> choices){
        List<String> allCourses = daoDriver.getAllCourseIDs();
        List<String> unselected = new ArrayList<>();

        for(String course : allCourses){
            if(!choices.contains(course))
                unselected.add(course);
        }

        return unselected;
    }
}
