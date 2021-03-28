package com.dkit.gd2.kevinmcenroe.client;

import com.dkit.gd2.kevinmcenroe.server.IStudentDAOInterface;
import com.dkit.gd2.kevinmcenroe.server.MySqlStudentDAO;
import com.dkit.gd2.kevinmcenroe.core.Student;
import com.dkit.gd2.kevinmcenroe.exceptions.DAOException;

import java.util.List;

// Adapted from sample code
// Database driver application
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Database access" );

        IStudentDAOInterface IStudentDAO = new MySqlStudentDAO();

        getAllStudents(IStudentDAO);
        Student sampleStudent = new Student(100, "1999-01-01", "password100");
        registerStudent(IStudentDAO, sampleStudent);
    }

    private static void getAllStudents(IStudentDAOInterface IStudentDAO)
    {
        try
        {
            List<Student> students = IStudentDAO.findAllStudents();
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

    private static void registerStudent(IStudentDAOInterface IStudentDAO, Student student)
    {
        //TODO Check if valid student or already exits
        try
        {
            IStudentDAO.registerStudent(student);
            //addedStudent.toString();
        }
        catch(DAOException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }
}
