//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.CourseDTO;

import java.util.List;

public interface ICourseDAOInterface
{
    CourseDTO getCourseByID(String courseID) throws DAOException;
    List<CourseDTO> getAllCourses() throws DAOException;
}