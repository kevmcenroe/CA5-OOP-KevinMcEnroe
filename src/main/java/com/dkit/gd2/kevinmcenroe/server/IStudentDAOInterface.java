//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.Student;

//Adapted from sample code
public interface IStudentDAOInterface
{
    boolean registerStudent(Student student) throws DAOException;
    boolean isRegistered(int caoNumber) throws DAOException;
    boolean logInStudent(Student student) throws DAOException;
}
