//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.Course;

import java.util.List;

public interface ICourseChoiceDAOInterface {
    public List<Course> getCourseChoicesByCAONumber(String caoNumber) throws DAOException;
    public boolean updateCourseChoices(String caoNumber) throws DAOException;
}
