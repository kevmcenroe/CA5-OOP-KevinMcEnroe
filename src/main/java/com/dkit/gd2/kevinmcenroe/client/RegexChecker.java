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
        /* This check should take place elsewhere, as this class handles regex only
        else if(isRegistered(Integer.parseInt(caoNumber))){
            System.out.println(Colours.RED + "A student of CAO Number " + caoNumber + " already exists" + Colours.RESET);
            return false;
        }*/
    }
}
