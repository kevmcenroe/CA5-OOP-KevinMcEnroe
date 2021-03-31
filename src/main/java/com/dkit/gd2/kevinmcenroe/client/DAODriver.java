//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.client;

import com.dkit.gd2.kevinmcenroe.server.*;
import com.dkit.gd2.kevinmcenroe.core.Student;

import com.dkit.gd2.kevinmcenroe.core.Course;

import java.util.ArrayList;
import java.util.List;

// Adapted from sample code
// Database driver application
public class DAODriver
{
    public void main( String[] args )
    {

        System.out.println( "Database Access" );
        Student sampleStudent = new Student(105, "1999-01-01", "password100");

        //Student DAO Interaction

        //isRegistered(IStudentDAO, sampleStudent.getCaoNumber());
        //registerStudent(IStudentDAO, sampleStudent);
        //logIn(IStudentDAO, sampleStudent);

        //Course DAO Interaction
        ICourseDAOInterface ICourseDAO = new MySqlCourseDAO();
        getCourseByCourseID(ICourseDAO, "DK001");
        getAllCourses(ICourseDAO);

        //CourseChoice DAO Interaction
        ICourseChoiceDAOInterface ICourseChoiceDAO = new MySqlCourseChoiceDAO();
        getCourseChoicesByCAONumber(ICourseChoiceDAO,1);

        List<String> choices = new ArrayList<>();
        choices.add("DK006");
        choices.add("DK002");
        choices.add("DK003");
        choices.add("DK004");
        choices.add("DK005");
        updateCourseChoices(ICourseChoiceDAO, 105, choices);

        Student sampleStudentB = new Student(109, "1999-01-01", "password100");
        List<String> choicesB = new ArrayList<>();
        choices.add("DK006");
        choices.add("DK002");
        choices.add("DK003");
        choices.add("DK004");
        choices.add("DK005");
        updateCourseChoices(ICourseChoiceDAO, 109, choicesB);
    }

    public void registerStudent(Student student)
    {
        IStudentDAOInterface IStudentDAO = new MySqlStudentDAO();
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

    public static void isRegistered(IStudentDAOInterface IStudentDAO, int caoNumber)
    {
        try
        {
            System.out.println("\nVerifying if student is registered (CAO Number: " + caoNumber + ")...");
            IStudentDAO.isRegistered(caoNumber);
        }
        catch(DAOException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }

    public static void logIn(IStudentDAOInterface IStudentDAO, Student student)
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

    public static void getCourseByCourseID(ICourseDAOInterface ICourseDAO, String courseID){
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

    public static void getAllCourses(ICourseDAOInterface ICourseDAO){
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

    public static void getCourseChoicesByCAONumber(ICourseChoiceDAOInterface ICourseChoiceDAO, int caoNumber){
        try
        {
            System.out.println("\nFinding course choices by CAO number (CAO Number: " + caoNumber +")...");
            List<Course> course = ICourseChoiceDAO.getCourseChoicesByCAONumber(caoNumber);
        }
        catch(DAOException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }

    public static void updateCourseChoices(ICourseChoiceDAOInterface ICourseChoiceDAO, int caoNumber, List<String> newChoicesByID){
        try
        {
            System.out.println("\nUpdating course choices using course IDs (CAO Number " + caoNumber +")...");
            boolean success = ICourseChoiceDAO.updateCourseChoices(caoNumber, newChoicesByID);
        }
        catch(DAOException daoe)
        {
            System.out.println(daoe.getMessage());
        }
    }
}
