package com.dkit.gd2.kevinmcenroe.client;

import com.dkit.gd2.kevinmcenroe.core.CAOService;
import com.dkit.gd2.kevinmcenroe.core.StudentDTO;

import java.util.List;

public class RequestGenerator {
    public String generateRegisterRequest(StudentDTO student){
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

    public String generateLogInRequest(StudentDTO student){
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

    public String generateLogOutRequest(){
        return CAOService.LOGOUT_COMMAND;
    }

    public String generateCourseRequest(String courseID){
        return CAOService.DISPLAY_COURSE_COMMAND + CAOService.BREAKING_CHARACTER + courseID;
    }

    public String generateAllCoursesRequest(){
        return CAOService.DISPLAY_ALL_COURSES_COMMAND;
    }

    public String generateCurrentChoicesRequest(int caoNumber){
        return CAOService.DISPLAY_CHOICES_COMMAND + CAOService.BREAKING_CHARACTER + caoNumber;
    }

    public String generateUpdateChoicesRequest(int caoNumber, List<String> newChoices){
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
}
