//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.Colours;
import com.dkit.gd2.kevinmcenroe.core.StudentDTO;
import com.dkit.gd2.kevinmcenroe.core.CourseDTO;

import java.util.ArrayList;
import java.util.List;

// Adapted from sample code
public class DAODriver
{
    public boolean registerStudent(StudentDTO student)
    {
        IStudentDAOInterface IStudentDAO = new MySqlStudentDAO();
        try
        {
            System.out.println("\nRegistering student (CAO Number " + student.getCaoNumber() +")...");
            return IStudentDAO.registerStudent(student);
        }
        catch(DAOException daoe)
        {
            System.out.println(Colours.RED + "registerStudent() - " + daoe.getMessage() + Colours.RESET);
            return false;
        }
    }

    public boolean logIn(StudentDTO student)
    {
        IStudentDAOInterface IStudentDAO = new MySqlStudentDAO();
        try
        {
            System.out.println("\nLogging in student (CAO Number: " + student.getCaoNumber() + ")...");

            return IStudentDAO.logInStudent(student);
        }
        catch(DAOException daoe)
        {
            System.out.println(Colours.RED + "logIn() - " + daoe.getMessage() + Colours.RESET);
            return false;
        }
    }

    public CourseDTO getCourseByCourseID(String courseID){
        ICourseDAOInterface ICourseDAO = new MySqlCourseDAO();
        try
        {
            System.out.println("\nFinding course by courseID (CourseID: " + courseID + ")...");
            return ICourseDAO.getCourseByID(courseID);
        }
        catch(DAOException daoe)
        {
            System.out.println(Colours.RED + "getCourseByCourseID() - " + daoe.getMessage() + Colours.RESET);
            return null;
        }
    }

    public List<CourseDTO> getAllCourses(){
        ICourseDAOInterface ICourseDAO = new MySqlCourseDAO();
        try
        {
            System.out.println("\nGetting all courses...");
            return ICourseDAO.getAllCourses();
        }
        catch(DAOException daoe)
        {
            System.out.println(Colours.RED + "getAllCourses() - " + daoe.getMessage() + Colours.RESET);
            return null;
        }
    }

    public List<String> getAllCourseIDs(){
        ICourseDAOInterface ICourseDAO = new MySqlCourseDAO();
        try
        {
            List<CourseDTO> allCourses = ICourseDAO.getAllCourses();
            List<String> allCourseIDs = new ArrayList<>();

            for(CourseDTO course : allCourses)
            {
                allCourseIDs.add(course.getCourseId());
            }
            return allCourseIDs;
        }
        catch(DAOException daoe)
        {
            System.out.println(Colours.RED + "getAllCourseIDs() - " + daoe.getMessage() + Colours.RESET);
            return null;
        }
    }

    public List<String> getCourseChoices(int caoNumber){
        ICourseChoiceDAOInterface ICourseChoiceDAO = new MySqlStudentCoursesDAO();
        try
        {
            System.out.println("\nGetting course choices (CAO Number " + caoNumber +")...");
            return ICourseChoiceDAO.getCourseChoicesByCAONumber(caoNumber);
        }
        catch(DAOException daoe)
        {
            System.out.println(Colours.RED + "getCourseChoices() - " + daoe.getMessage() + Colours.RESET);
            return null;
        }
    }

    public boolean updateCourseChoices(int caoNumber, List<String> newChoicesByID){
        ICourseChoiceDAOInterface ICourseChoiceDAO = new MySqlStudentCoursesDAO();
        try
        {
            if(newChoicesByID != null) {
                System.out.println("\nUpdating course choices (CAO Number " + caoNumber + ")...");
                return ICourseChoiceDAO.updateCourseChoices(caoNumber, newChoicesByID);
            }
            else{
                System.out.println(Colours.RED + "updateCourseChoices() - newChoicesByID is null" + Colours.RESET);
                return false;
            }
        }
        catch(DAOException daoe)
        {
            System.out.println(Colours.RED + "updateCourseChoices() - " + daoe.getMessage() + Colours.RESET);
            return false;
        }
    }
}
