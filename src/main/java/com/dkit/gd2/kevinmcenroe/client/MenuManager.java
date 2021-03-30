package com.dkit.gd2.kevinmcenroe.client;

import java.util.Scanner;

public class MenuManager {

    private static final Scanner keyboard = new Scanner(System.in);
    private String getInput(String request, InputType inputType) {
        String input;
        System.out.print("Please enter " + request + " :>");
        RegexChecker regexChecker = new RegexChecker();

        switch (inputType)
        {
            case CAO_NUMBER:
                input = keyboard.nextLine();
                while(!regexChecker.checkCAONumber(input)){
                    System.out.print("Please enter " + request + " :>");
                    input = keyboard.nextLine();
                }
                return input;
            case DATE_OF_BIRTH:

                break;
            case PASSWORD:

                break;
            case IDLE_COURSE_ID:
                break;
            case CHOICE_COURSE_ID:
                break;
        }
        return null;
    }

    //Student DAO Menus
    public void displayRegisterStudent(){
        String input = getInput("CAO Number", InputType.CAO_NUMBER);
    }

    public void displayIsRegistered(){

    }

    public void displayLogInStudent(){

    }

    //Course DAO Menus
    public void displayGetCourseByID(){

    }

    public void displayGetAllCourses(){

    }

    // CourseChoice DAO Menus
    public void displayGetCourseChoicesByCAONumber(){

    }

    public void displayUpdateCourseChoices(){

    }
}
