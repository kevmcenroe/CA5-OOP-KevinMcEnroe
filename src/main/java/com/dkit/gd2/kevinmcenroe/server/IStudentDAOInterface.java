//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.Student;

//Adapted from sample code
public interface IStudentDAOInterface
{
    public boolean registerStudent(Student student) throws DAOException;
    public boolean isRegistered(Student student) throws DAOException;
    public boolean logInStudent(Student student) throws DAOException;
}
