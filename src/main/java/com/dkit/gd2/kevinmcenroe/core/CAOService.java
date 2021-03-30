//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.core;

/* The CAOService class has constants to define all of the messages that are sent between the Client and Server
 */

public class CAOService
{
    public static final int PORT_NUM = 50025;
    public static final String HOSTNAME = "localhost";
    public static final String BREAKING_CHARACTER = "%%";

    public static final String REGISTER_COMMAND = "REGISTER";
    public static final String SUCCESSFUL_REGISTER = "REGISTERED";
    public static final String FAILED_REGISTER = "REG FAILED";

    public static final String LOGIN_COMMAND = "LOGIN";
    public static final String SUCCESSFUL_LOGIN = "LOGGED IN";
    public static final String FAILED_LOGIN = "LOGIN FAILED";

    public static final String LOGOUT_COMMAND = "LOGOUT";
    public static final String SUCCESSFUL_LOGOUT = "LOGGED OUT";

    public static final String DISPLAY_COURSE_COMMAND = "DISPLAY COURSE";
    public static final String SUCCESSFUL_DISPLAY_COURSE = "";
    public static final String FAILED_DISPLAY_COURSE = "DISPLAY FAILED";

    public static final String DISPLAY_ALL_COURSES_COMMAND = "DISPLAY_ALL";
    public static final String SUCCESSFUL_DISPLAY_ALL_COURSES = "SUCCESSFUL DISPLAY ALL";
    public static final String FAILED_DISPLAY_ALL_COURSES = "FAILED DISPLAY ALL";

    public static final String DISPLAY_CHOICES_COMMAND = "DISPLAY CURRENT";
    public static final String SUCCESSFUL_DISPLAY_CHOICES = "SUCCESSFUL DISPLAY CURRENT";
    public static final String FAILED_DISPLAY_CHOICES = "FAILED DISPLAY CURRENT";

    public static final String UPDATE_CHOICES_COMMAND = "UPDATE CURRENT";
    public static final String SUCCESSFUL_UPDATE_CHOICES = "SUCCESSFUL UPDATE CURRENT";
    public static final String FAILED_UPDATE_CHOICES = "FAILED UPDATE CURRENT";
}
