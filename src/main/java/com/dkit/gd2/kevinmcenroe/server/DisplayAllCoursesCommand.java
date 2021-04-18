package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.CAOService;
import com.dkit.gd2.kevinmcenroe.core.Course;

import java.util.List;

public class DisplayAllCoursesCommand implements ICommand {
    @Override
    public String generateResponse(String[] components, DAODriver daoDriver) {
        List<String> gotCourseIDs = daoDriver.getAllCourseIDs();
        if (gotCourseIDs == null)
            return CAOService.FAILED_DISPLAY_ALL_COURSES;

        else {
            //This approach was used for modularity and easy editing of response
            StringBuilder response = new StringBuilder();
            response.append(CAOService.SUCCESSFUL_DISPLAY_ALL_COURSES);
            response.append(CAOService.BREAKING_CHARACTER);
            
            for(String courseID : gotCourseIDs)
            {
                Course course = daoDriver.getCourseByCourseID(courseID);
                response.append(course.getCourseId());
                response.append(CAOService.BREAKING_CHARACTER);
                response.append(course.getLevel());
                response.append(CAOService.BREAKING_CHARACTER);
                response.append(course.getTitle());
                response.append(CAOService.BREAKING_CHARACTER);
                response.append(course.getInstitution());
                response.append(CAOService.COURSE_SEPARATOR);
            }


            return response.toString();
        }
    }
}
