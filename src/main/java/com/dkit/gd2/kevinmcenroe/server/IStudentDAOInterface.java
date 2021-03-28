//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.Student;

import java.util.List;

//Adapted from sample code
public interface IStudentDAOInterface
{
    //public List<Student> findAllStudents() throws DAOException;
    //public Student findStudentByCAONumberPassword(String caoNumber, String password) throws DAOException;
    public void registerStudent(Student student) throws DAOException;
    public boolean isRegistered(Student student) throws DAOException;
    public boolean logInStudent(Student student) throws DAOException;
}
