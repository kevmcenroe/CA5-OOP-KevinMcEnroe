//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.client;

import com.dkit.gd2.kevinmcenroe.server.*;
import com.dkit.gd2.kevinmcenroe.core.Student;

import com.dkit.gd2.kevinmcenroe.core.Course;

import java.util.List;

// Adapted from sample code
// Database driver application
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Database Access" );
        Student sampleStudent = new Student(105, "1999-01-01", "password100");

        //Student DAO Interaction
        IStudentDAOInterface IStudentDAO = new MySqlStudentDAO();
        isRegistered(IStudentDAO, sampleStudent);
        registerStudent(IStudentDAO, sampleStudent);
        logIn(IStudentDAO, sampleStudent);

        //Course DAO Interaction
        ICourseDAOInterface ICourseDAO = new MySqlCourseDAO();
        getCourseByCourseID(ICourseDAO, "DK001");
        getAllCourses(ICourseDAO);

        //CourseChoice DAO Interaction
        ICourseChoiceDAOInterface ICourseChoiceDAO = new MySqlCourseChoiceDAO();
        getCourseChoicesByCAONumber(ICourseChoiceDAO,"1");
    }

    private static void registerStudent(IStudentDAOInterface IStudentDAO, Student student)
    {
        //TODO Check if student is valid or already exits
        //TODO Employ RegexChecker on input elsewhere
        try
        {
            System.out.println("\nRegistering student (CAO Number " + student.getCaoNumber() +")...");
            IStudentDAO.registerStudent(student);
        }
        catch(DAOException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }

    private static void isRegistered(IStudentDAOInterface IStudentDAO, Student student)
    {
        try
        {
            System.out.println("\nVerifying if student is registered (CAO Number: " + student.getCaoNumber() + ")...");
            IStudentDAO.isRegistered(student);
        }
        catch(DAOException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }

    private static void logIn(IStudentDAOInterface IStudentDAO, Student student)
    {
        try
        {
            System.out.println("\nLogging in student (CAO Number: " + student.getCaoNumber() + ")...");
            IStudentDAO.logInStudent(student);
        }
        catch(DAOException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }

    private static void getCourseByCourseID(ICourseDAOInterface ICourseDAO, String courseID){
        try
        {
            System.out.println("\nFinding course by courseID (CourseID: " + courseID + ")...");
            Course course = ICourseDAO.getCourseByID(courseID);
        }
        catch(DAOException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }

    private static void getAllCourses(ICourseDAOInterface ICourseDAO){
        try
        {
            System.out.println("\nGetting all courses...");
            List<Course> course = ICourseDAO.getAllCourses();
        }
        catch(DAOException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }

    private static void getCourseChoicesByCAONumber(ICourseChoiceDAOInterface ICourseChoiceDAO, String caoNumber){
        try
        {
            System.out.println("\nFinding course choices by CAO number (CAO Number " + caoNumber +")...");
            List<Course> course = ICourseChoiceDAO.getCourseChoicesByCAONumber(caoNumber);
        }
        catch(DAOException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }

    /*
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
*/
}
