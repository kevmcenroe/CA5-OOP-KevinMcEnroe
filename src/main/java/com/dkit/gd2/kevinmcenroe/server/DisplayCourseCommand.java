package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.CAOService;
import com.dkit.gd2.kevinmcenroe.core.Course;

public class DisplayCourseCommand implements ICommand {
    @Override
    public String generateResponse(String[] components, DAODriver daoDriver) {
        Course gotCourse = daoDriver.getCourseByCourseID(components[1]);
        if (gotCourse == null)
            return CAOService.FAILED_DISPLAY_COURSE;

        else {
            //This approach was used for modularity and easy editing of response
            StringBuilder response = new StringBuilder();
            response.append(CAOService.SUCCESSFUL_DISPLAY_COURSE);
            response.append(CAOService.BREAKING_CHARACTER);

            response.append(gotCourse.getCourseId());
            response.append(CAOService.BREAKING_CHARACTER);
            response.append(gotCourse.getLevel());
            response.append(CAOService.BREAKING_CHARACTER);
            response.append(gotCourse.getTitle());
            response.append(CAOService.BREAKING_CHARACTER);
            response.append(gotCourse.getInstitution());

            return response.toString();
        }
    }
}
