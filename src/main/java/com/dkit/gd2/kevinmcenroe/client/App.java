package com.dkit.gd2.kevinmcenroe.client;

import com.dkit.gd2.kevinmcenroe.server.IStudentDAOInterface;
import com.dkit.gd2.kevinmcenroe.server.MySqlStudentDAO;
import com.dkit.gd2.kevinmcenroe.core.Student;
import com.dkit.gd2.kevinmcenroe.exceptions.DAOException;
import com.dkit.gd2.kevinmcenroe.core.Student;
import com.dkit.gd2.kevinmcenroe.server.IStudentDAOInterface;
import com.dkit.gd2.kevinmcenroe.server.MySqlStudentDAO;

import java.util.List;

// Adapted from sample code
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Database access" );

        IStudentDAOInterface IStudentDAO = new MySqlStudentDAO();

        getAllStudents(IStudentDAO);
    }

    private static void getAllStudents(IStudentDAOInterface IStudentDAO)
    {
        try
        {
            List<Student> students = IStudentDAO.findAllUsers();
            printStudents(students);
        }
        catch(DAOException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }

    private static void printStudents(List<Student> students)
    {
        if(students == null || students.isEmpty())
        {
            System.out.println("There are no students");
        }

        for(Student student : students)
        {
            System.out.println(student);
        }
    }
}
