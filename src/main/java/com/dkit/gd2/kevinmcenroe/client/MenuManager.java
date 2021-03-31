package com.dkit.gd2.kevinmcenroe.client;

import com.dkit.gd2.kevinmcenroe.core.Student;

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
            case IDLE_COURSE_ID:
                break;
            case CHOICE_COURSE_ID:
                break;
        }
        return null;
    }

    //Student DAO Menus

    public Student displayRegisterStudentMenu(){
        int caoNumber = Integer.parseInt(getInput("CAO Number", InputType.CAO_NUMBER));
        String dateOfBirth = getInput("Date of Birth in YYYY-MM-DD format", InputType.DATE_OF_BIRTH);
        String password = getInput("Password (Minimum 8 characters)", InputType.PASSWORD);

        return new Student(caoNumber, dateOfBirth, password);
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
