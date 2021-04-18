//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.client;

import com.dkit.gd2.kevinmcenroe.core.CAOService;
import com.dkit.gd2.kevinmcenroe.core.Colours;
import com.dkit.gd2.kevinmcenroe.core.StudentDTO;

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
            case ANY_COURSE_ID:
                while(!input.equals("X") && !regexChecker.checkAnyCourseID(input)){
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

    public StudentDTO displayStudentMenu(){
        int caoNumber = Integer.parseInt(Objects.requireNonNull(getInput("CAO Number", InputType.CAO_NUMBER)));
        String dateOfBirth = getInput("Date of Birth in YYYY-MM-DD format", InputType.DATE_OF_BIRTH);
        String password = getInput("Password (Minimum 8 characters)", InputType.PASSWORD);

        return new StudentDTO(caoNumber, dateOfBirth, password);
    }

    public String displayGetCourseMenu(){
        return getInput("Course ID", InputType.ANY_COURSE_ID);
    }

    public List<String> displayUpdateCourseChoicesMenu(int caoNumber, List<String> allAvailableCourseIDs){
        List<String> newChoices = new ArrayList<>();
        System.out.println("Enter your 10 updated course choices");

        for(int i = 1; i<11; i++){
            String courseID = getInput("Course ID [Choice " + i + "] or X to save and exit", InputType.ANY_COURSE_ID);
            courseID = verifyCourseID(allAvailableCourseIDs, courseID, newChoices, i);

            if(courseID.equals("X")) {
                System.out.println("Saved and sent course choices");
                break;
            }
            else
                newChoices.add(courseID);
        }
        return  newChoices;
    }

    private String verifyCourseID(List<String> allAvailableCourseIDs, String courseID, List<String> newChoices, int i){
        while(!allAvailableCourseIDs.contains(courseID) && !courseID.equals("X")) {
            System.out.println(Colours.RED + "Course ID \"" + courseID + "\" does not exist" + Colours.RESET);
            courseID = getInput("Course ID [Choice " + i + "] or X to save and exit", InputType.ANY_COURSE_ID);
        }

        while(newChoices.contains(courseID))
        {
            System.out.println(Colours.RED + "You have already submitted course ID \"" + courseID + "\" as a choice" + Colours.RESET);
            courseID = getInput("Course ID [Choice " + i + "] or X to save and exit", InputType.ANY_COURSE_ID);
        }

        return courseID;
    }


    public void displayParsedCourse(String response){
        if(!response.equals(CAOService.FAILED_DISPLAY_COURSE)) {
            String[] fields = response.split(CAOService.BREAKING_CHARACTER);
            System.out.println("Displaying course...");

            String courseID = fields[0];
            String level = fields[1];
            String title = fields[2];
            String institution = fields[3];

            System.out.println(Colours.GREEN + "Course ID = " + courseID + ", Level = " + level + ", Title = " + title + ", Institution = " + institution + Colours.RESET);
        }
        else
            System.out.println(Colours.RED + "A course of given course ID does not exist" + Colours.RESET);
    }

    public List<String> displayParsedAllCourses(String response){
        List<String> allCourseIDs = new ArrayList<>();
        if(!response.equals(CAOService.FAILED_DISPLAY_CHOICES)) {
            String[] courseComponents = response.split(CAOService.COURSE_SEPARATOR);
            System.out.println("Displaying all courses...");

            int courseIndex = 0;
            for (String course : courseComponents) {
                String[] fields = course.split(CAOService.BREAKING_CHARACTER);
                int i = 0;
                if (courseIndex == 0)
                    i++;
                String courseID = fields[i];
                String level = fields[i + 1];
                String title = fields[i + 2];
                String institution = fields[i + 3];

                System.out.println(Colours.GREEN + "Course ID = " + courseID + ", Level = " + level + ", Title = " + title + ", Institution = " + institution + Colours.RESET);
                allCourseIDs.add(courseID);
                courseIndex++;
            }
        }
        else
            System.out.println(Colours.RED + "No choices registered for that CAO number" + Colours.RESET);

        return allCourseIDs;
    }

    public void displayParsedCurrentChoices(String response){
        if(!response.equals(CAOService.FAILED_DISPLAY_CHOICES)) {
            String[] choices = response.split(CAOService.BREAKING_CHARACTER);
            System.out.println("Displaying current choices...");

            int choiceIndex = 0;
            for (String choice : choices) {
                if (choiceIndex != 0)
                    System.out.print(Colours.GREEN + "[#" + choiceIndex + "] " + choice + " " + Colours.RESET);
                choiceIndex++;
            }
            System.out.println();
        }
    }

    /* The following code was redeveloped to maintain client/server encapsulation and avoid DAODriver access from Client

    private List<String> getUnselectedCourses(DAODriver daoDriver, List<String> choices){
        List<String> allCourses = daoDriver.getAllCourseIDs();
        List<String> unselected = new ArrayList<>();

        for(String course : allCourses){
            if(!choices.contains(course))
                unselected.add(course);
        }

        return unselected;
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
    }*/
}
