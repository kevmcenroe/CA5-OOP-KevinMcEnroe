//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.client;

/* This class should contain static methods to verify input in the application
 */

import com.dkit.gd2.kevinmcenroe.core.Colours;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexChecker
{

    // checks for date of birth, etc
    public boolean checkCAONumber(String caoNumber){

        String caoNumberPattern = "^[0-9]*$";
        Pattern pattern = Pattern.compile((caoNumberPattern));
        Matcher matcher = pattern.matcher(caoNumber);

        if(!matcher.matches()) {
            System.out.println(Colours.RED + "Invalid CAO Number" + Colours.RESET);
            return false;
        }
        else
        {
            return true;
        }
        //TODO Link this to CAOClient to check if CAO Number is already registered
        /*
        else if(isRegistered(Integer.parseInt(caoNumber))){
            System.out.println(Colours.RED + "A student of CAO Number " + caoNumber + " already exists" + Colours.RESET);
            return false;
        }*/
    }

    public boolean checkDateOfBirth(String dateOfBirth){
        // This regex was adapted from https://stackoverflow.com/questions/2149680/regex-date-format-validation-on-java
        String dateOfBirthPattern = "((18|19|20|21)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
        Pattern pattern = Pattern.compile((dateOfBirthPattern));
        Matcher matcher = pattern.matcher(dateOfBirth);

        if(!matcher.matches()) {
            System.out.println(Colours.RED + "Invalid Date of Birth" + Colours.RESET);
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean checkPassword(String password){
        //This regex was adapted from https://stackoverflow.com/questions/2149680/regex-date-format-validation-on-java
        String passwordPattern = "^.{8,}$";
        Pattern pattern = Pattern.compile((passwordPattern));
        Matcher matcher = pattern.matcher(password);

        if(!matcher.matches()) {
            System.out.println(Colours.RED + "Invalid Password" + Colours.RESET);
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean checkCourseID(String courseID){
        String courseIDPattern = "^[a-zA-Z0-9]*$";
        Pattern pattern = Pattern.compile((courseIDPattern));
        Matcher matcher = pattern.matcher(courseID);

        if(!matcher.matches()) {
            System.out.println(Colours.RED + "Invalid CourseID" + Colours.RESET);
            return false;
        }
        else
        {
            return true;
        }
    }
}
