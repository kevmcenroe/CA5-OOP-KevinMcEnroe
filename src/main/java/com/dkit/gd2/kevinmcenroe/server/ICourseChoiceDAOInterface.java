//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import java.util.List;

public interface ICourseChoiceDAOInterface {
    List<String> getCourseChoicesByCAONumber(int caoNumber) throws DAOException;
    boolean updateCourseChoices(int caoNumber, List<String> newChoices) throws DAOException;
}
