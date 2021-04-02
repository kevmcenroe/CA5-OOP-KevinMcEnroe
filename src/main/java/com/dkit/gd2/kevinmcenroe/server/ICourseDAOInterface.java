//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.Course;

import java.util.List;

public interface ICourseDAOInterface
{
    Course getCourseByID(String courseID) throws DAOException;
    List<Course> getAllCourses() throws DAOException;
}