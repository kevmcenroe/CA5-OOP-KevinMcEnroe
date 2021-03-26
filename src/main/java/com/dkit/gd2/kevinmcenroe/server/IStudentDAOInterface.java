package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.Student;
import com.dkit.gd2.kevinmcenroe.core.Course;
import com.dkit.gd2.kevinmcenroe.exceptions.DAOException;

import java.util.List;

//Adapted from sample code
public interface IStudentDAOInterface
{
    public List<Student> findAllUsers() throws DAOException;
    public Student findUserByCAONumberPassword(String caoNumber, String password) throws DAOException;
}
