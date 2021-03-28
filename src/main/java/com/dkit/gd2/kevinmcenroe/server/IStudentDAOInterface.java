package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.Student;
import com.dkit.gd2.kevinmcenroe.core.Course;
import com.dkit.gd2.kevinmcenroe.exceptions.DAOException;

import java.util.List;

//Adapted from sample code
public interface IStudentDAOInterface
{
    public List<Student> findAllStudents() throws DAOException;
    public Student findStudentByCAONumberPassword(String caoNumber, String password) throws DAOException;
    public void registerStudent(Student student) throws DAOException;
}
