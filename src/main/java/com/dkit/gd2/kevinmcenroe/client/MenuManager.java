package com.dkit.gd2.kevinmcenroe.client;

import com.dkit.gd2.kevinmcenroe.core.Colours;
import com.dkit.gd2.kevinmcenroe.core.Course;
import com.dkit.gd2.kevinmcenroe.core.Student;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuManager {

    private static final Scanner keyboard = new Scanner(System.in);
    private String getInput(String request, InputType inputType) {
        String input;
        System.out.print("Please enter " + request + " :>");
        RegexChecker regexChecker = new RegexChecker();

        input = keyboard.nextLine();
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
            case COURSE_ID:
                while(!regexChecker.checkCourseID(input)){
                    System.out.print("Please enter " + request + " :>");
                    input = keyboard.nextLine();
                }
                return input;
            case CHOICE_COURSE_ID:
                break;
        }
        return null;
    }

    //Adapted from my CA4 submission
    public static void displayMainMenu()
    {
        System.out.println("\nMenu Options:");
        for(int i=0; i < StartMenu.values().length; i++)
        {
            String menuOption = StartMenu.values()[i].toString().replaceAll("_", " ");
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
            String menuOption = LoggedInMenu.values()[i].toString().replaceAll("_", " ");
            System.out.println("\t" + Colours.BLUE + i + ". " + menuOption + Colours.RESET);
        }
        System.out.println("Enter the corresponding number to select an option");
    }

    //Student DAO Menus

    public Student displayStudentMenu(){
        int caoNumber = Integer.parseInt(getInput("CAO Number", InputType.CAO_NUMBER));
        String dateOfBirth = getInput("Date of Birth in YYYY-MM-DD format", InputType.DATE_OF_BIRTH);
        String password = getInput("Password (Minimum 8 characters)", InputType.PASSWORD);

        return new Student(caoNumber, dateOfBirth, password);
    }

    public String displayGetCourseMenu(){
        String courseID = getInput("Course ID", InputType.COURSE_ID);
        return courseID;
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

        List<String> choicesByID = new ArrayList<>();
        DAODriver daoDriver = new DAODriver();
        ChoiceActions menuOption = ChoiceActions.values()[option];
        switch (menuOption) {
            case UPDATE_ALL_CHOICES:
                System.out.println("Enter your 10 choices");

                for(int i = 1; i<11; i++){
                    if(i>1)
                        System.out.println();

                    String courseID = getInput("Course ID [Choice " + i + "]", InputType.COURSE_ID);
                    while(choicesByID.contains(courseID))
                    {
                        System.out.println(Colours.RED + "You have already submitted course ID \"" + courseID + "\"" + Colours.RESET);
                        courseID = getInput("Course ID [Choice " + i + "]", InputType.COURSE_ID);
                    }
                    newChoices.add(courseID);
                }

                System.out.println("\nSubmitted choices:");
                for(int i = 0; i<newChoices.size(); i++)
                {
                    System.out.println("[Choice "+ (i+1) +"]"+ newChoices.get(i));
                }
                return newChoices;
            case UPDATE_ONE_CHOICE:
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
            String menuOption = ChoiceActions.values()[i].toString().replaceAll("_", " ");
            System.out.println("\t" + Colours.BLUE + i + ". " + menuOption + Colours.RESET);
        }
        System.out.println("Enter the corresponding number to select an option");
    }

    public void displayCurrentChoices(int caoNumber){
        DAODriver daoDriver = new DAODriver();
        List<String> allChoices = daoDriver.getCourseChoices(caoNumber);
        if(allChoices != null){
            for(int i = 0; i<allChoices.size(); i++)
                System.out.println("[Choice" + i + "] " + Colours.GREEN + allChoices.get(i) + Colours.RESET);
        }
    }

    private List<String> displayChangeChoiceMenu(int caoNumber) {
        DAODriver daoDriver = new DAODriver();
        List<String> allChoices = daoDriver.getCourseChoices(caoNumber);


        System.out.println("\nCurrent Choices:");
        for(int i = 0; i < allChoices.size(); i++)
        {
            String choice = allChoices.get(i);
            System.out.println("\t" + Colours.BLUE + i + ". " + choice + Colours.RESET);
        }
        System.out.println("Select the choice you would like to update");

        //---------------

        int option;
        String input = keyboard.nextLine();
        if (input.length() != 1)
            throw new IllegalArgumentException();
        else
            option = Integer.parseInt(input);

        if (option < 0 || option >= 9)
            throw new IllegalArgumentException();

        System.out.println("You have selected choice " + option + ": " + Colours.BLUE + allChoices.get(option) + Colours.RESET);
        String courseID = getInput("Course ID [Choice " + option + "]", InputType.COURSE_ID);
        while(allChoices.contains(courseID))
        {
            System.out.println(Colours.RED + courseID + " already exists as choice " + allChoices.indexOf(courseID) + Colours.RESET);
            System.out.println("Please enter another course or overwrite choice " + allChoices.indexOf(courseID) + " to free up course " + courseID);
            System.out.println("Available Courses: " + Colours.BLUE + getUnselectedCourses(daoDriver, allChoices) + Colours.RESET);
            courseID = getInput("Course ID [Choice " + option + "]", InputType.COURSE_ID);
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
