//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.CAOService;
//Demonstrates the Factory pattern - this factory creates a command of the requested type

public class CommandFactory
{
    public static ICommand createCommand(String requestCommand)
    {
        ICommand c = null;
        switch(requestCommand)
        {
            case CAOService.REGISTER_COMMAND:
                c = new RegisterCommand();
                break;
            case CAOService.LOGIN_COMMAND:
                c = new LoginCommand();
                break;
            case CAOService.LOGOUT_COMMAND:
                c = new LogoutCommand();
                break;
            case CAOService.DISPLAY_COURSE_COMMAND:
                c = new DisplayCourseCommand();
                break;
            case CAOService.DISPLAY_ALL_COURSES_COMMAND:
                c = new DisplayAllCoursesCommand();
                break;
            case CAOService.DISPLAY_CHOICES_COMMAND:
                c = new DisplayChoicesCommand();
                break;
            case CAOService.UPDATE_CHOICES_COMMAND:
                c = new UpdateChoicesCommand();
                break;
        }
        return c;
    }
}
