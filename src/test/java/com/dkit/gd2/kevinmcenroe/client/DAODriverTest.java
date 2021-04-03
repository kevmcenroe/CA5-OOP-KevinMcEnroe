package com.dkit.gd2.kevinmcenroe.client;

import com.dkit.gd2.kevinmcenroe.core.Course;
import com.dkit.gd2.kevinmcenroe.core.Student;
import com.dkit.gd2.kevinmcenroe.server.*;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class DAODriverTest extends TestCase {

    public void testRegisterStudent() throws DAOException {
        Student student = new Student(87654321, "1999-01-01", "mypassword");
        IStudentDAOInterface IStudentDAO = new MySqlStudentDAO();

        IStudentDAO.registerStudent(student);

        boolean registered = IStudentDAO.isRegistered(student.getCaoNumber());
        assertTrue(registered);
    }

    public void testIsRegistered() throws DAOException {
        Student regStudent = new Student(87654321, "2000-02-02", "password2");
        IStudentDAOInterface IStudentDAO = new MySqlStudentDAO();
        IStudentDAO.registerStudent(regStudent);

        boolean registered = IStudentDAO.isRegistered(regStudent.getCaoNumber());
        boolean validLogIn = IStudentDAO.logInStudent(regStudent);
        assertEquals(registered, validLogIn);

        Student unregStudent = new Student(99999999, "1999-09-09", "password9");
        boolean unregistered = IStudentDAO.isRegistered(unregStudent.getCaoNumber());
        boolean invalidLogIn = IStudentDAO.logInStudent(unregStudent);
        assertEquals(unregistered, invalidLogIn);
    }

    public void testGetCourseByCourseID() throws DAOException {
        ICourseDAOInterface ICourseDAO = new MySqlCourseDAO();

        String courseID = "DK001";
        //The test requires that a course of this ID exist in the database
        //This courseID can be changed to match any other existing course

        Course gotCourse = ICourseDAO.getCourseByID(courseID);
        assertEquals(gotCourse.getCourseId(), courseID);
    }

    public void testLogInStudent() throws DAOException {
        Student regStudent = new Student(99999991, "2000-02-02", "password2");
        IStudentDAOInterface IStudentDAO = new MySqlStudentDAO();
        IStudentDAO.registerStudent(regStudent);

        boolean validLogIn = IStudentDAO.logInStudent(regStudent);
        assertTrue(validLogIn);

        Student unregStudent = new Student(99999992, "1999-09-09", "password9");
        boolean invalidLogIn = IStudentDAO.logInStudent(unregStudent);
        assertFalse(invalidLogIn);
    }

    public void testGetAllCourses() throws DAOException {
        //Should additional courses be added to the database, this test should be updated to reflect that
        List<Course> allCourses = new ArrayList<>();
        Course courseAA = new Course("DK001", "8", "Accounting and Finance", "DKIT");
        Course courseAB = new Course("DK002", "8", "Business Studies", "DKIT");
        Course courseAC = new Course("DK003", "8", "Computing in Games Development", "DKIT");
        Course courseAD = new Course("DK004", "8", "Computing in Software Development", "DKIT");
        Course courseAE = new Course("DK005", "8", "Augmented and Virtual Reality", "DKIT");
        Course courseAF = new Course("DK006", "8", "Mathematics and Data Science", "DKIT");
        Course courseAG = new Course("DK007", "8", "Computing Systems and Operations", "DKIT");
        Course courseAH = new Course("DK008", "8", "Building Surveying", "DKIT");
        Course courseAI = new Course("DK009", "7", "Creative Media", "DKIT");
        Course courseAJ = new Course("DK010", "7", "Music", "DKIT");

        Course courseBA = new Course("TC001", "8", "Acting", "TCD");
        Course courseBB = new Course("TC002", "8", "Ancient History", "TCD");
        Course courseBC = new Course("TC003", "8", "Business Studies and French", "TCD");
        Course courseBD = new Course("TC004", "8", "Business Studies and German", "TCD");
        Course courseBE = new Course("TC005", "8", "Business Studies and Polish", "TCD");
        Course courseBF = new Course("TC006", "8", "Classical Civilisation", "TCD");
        Course courseBG = new Course("TC007", "7", "Classical Languages", "TCD");
        Course courseBH = new Course("TC008", "7", "Clinical Speach and Language", "TCD");
        Course courseBI = new Course("TC009", "7", "English Studies", "TCD");
        Course courseBJ = new Course("TC010", "7", "German", "TCD");

        allCourses.add(courseAA);
        allCourses.add(courseAB);
        allCourses.add(courseAC);
        allCourses.add(courseAD);
        allCourses.add(courseAE);
        allCourses.add(courseAF);
        allCourses.add(courseAG);
        allCourses.add(courseAH);
        allCourses.add(courseAI);
        allCourses.add(courseAJ);

        allCourses.add(courseBA);
        allCourses.add(courseBB);
        allCourses.add(courseBC);
        allCourses.add(courseBD);
        allCourses.add(courseBE);
        allCourses.add(courseBF);
        allCourses.add(courseBG);
        allCourses.add(courseBH);
        allCourses.add(courseBI);
        allCourses.add(courseBJ);

        DAODriver daoDriver = new DAODriver();
        List<Course> gotCourses = daoDriver.getAllCourses();
        boolean equals = allCourses.equals(gotCourses);

        assertTrue(equals);
    }

    public void testGetAllCourseIDs() {
        //Should additional courses be added to the database, this test should be updated to reflect that
        List<String> allCourses = new ArrayList<>();
        String courseAA ="DK001";
        String courseAB = "DK002";
        String courseAC = "DK003";
        String courseAD ="DK004";
        String courseAE = "DK005";
        String courseAF = "DK006";
        String courseAG = "DK007";
        String courseAH = "DK008";
        String courseAI = "DK009";
        String courseAJ = "DK010";

        String courseBA = "TC001";
        String courseBB = "TC002";
        String courseBC = "TC003";
        String courseBD = "TC004";
        String courseBE = "TC005";
        String courseBF = "TC006";
        String courseBG = "TC007";
        String courseBH = "TC008";
        String courseBI = "TC009";
        String courseBJ = "TC010";

        allCourses.add(courseAA);
        allCourses.add(courseAB);
        allCourses.add(courseAC);
        allCourses.add(courseAD);
        allCourses.add(courseAE);
        allCourses.add(courseAF);
        allCourses.add(courseAG);
        allCourses.add(courseAH);
        allCourses.add(courseAI);
        allCourses.add(courseAJ);

        allCourses.add(courseBA);
        allCourses.add(courseBB);
        allCourses.add(courseBC);
        allCourses.add(courseBD);
        allCourses.add(courseBE);
        allCourses.add(courseBF);
        allCourses.add(courseBG);
        allCourses.add(courseBH);
        allCourses.add(courseBI);
        allCourses.add(courseBJ);

        DAODriver daoDriver = new DAODriver();
        List<String> gotCourses = daoDriver.getAllCourseIDs();
        boolean equals = allCourses.equals(gotCourses);

        assertTrue(equals);
    }

    public void testGetCourseChoices() {
        //This test requires that the student of caoNumber 999999999 remains in the database
        //Should this entry be manually removed, a replacement caoNumber should be included here
        int caoNumber = 99999999;
        String choiceA = "DK010";
        String choiceB = "DK009";
        String choiceC = "DK008";
        String choiceD = "DK007";
        String choiceE = "DK006";
        String choiceF = "DK005";
        String choiceG = "DK004";
        String choiceH = "DK003";
        String choiceI = "DK002";
        String choiceJ = "DK001";

        List<String> choices = new ArrayList<>();
        choices.add(choiceA);
        choices.add(choiceB);
        choices.add(choiceC);
        choices.add(choiceD);
        choices.add(choiceE);
        choices.add(choiceF);
        choices.add(choiceG);
        choices.add(choiceH);
        choices.add(choiceI);
        choices.add(choiceJ);

        DAODriver daoDriver = new DAODriver();
        daoDriver.updateCourseChoices(caoNumber, choices);
        List<String> gotChoices = daoDriver.getCourseChoices(caoNumber);

        assertEquals(choices, gotChoices);
    }

    public void testUpdateCourseChoices() {
        //This test requires that the student of caoNumber 999999999 remains in the database
        //Should this entry be manually removed, a replacement caoNumber should be included here
        int caoNumber = 99999999;
        String choiceA = "TC001";
        String choiceB = "TC002";
        String choiceC = "TC003";
        String choiceD = "TC004";
        String choiceE = "TC005";
        String choiceF = "TC006";
        String choiceG = "TC007";
        String choiceH = "TC008";
        String choiceI = "TC009";
        String choiceJ = "TC010";

        List<String> choices = new ArrayList<>();
        choices.add(choiceA);
        choices.add(choiceB);
        choices.add(choiceC);
        choices.add(choiceD);
        choices.add(choiceE);
        choices.add(choiceF);
        choices.add(choiceG);
        choices.add(choiceH);
        choices.add(choiceI);
        choices.add(choiceJ);

        DAODriver daoDriver = new DAODriver();
        daoDriver.updateCourseChoices(caoNumber, choices);
        List<String> allUpdatedChoices = daoDriver.getCourseChoices(caoNumber);

        assertEquals(choices, allUpdatedChoices);

        String choiceK = "DK001";
        choices.add(0, choiceK);
        choices.remove(1);

        daoDriver.updateCourseChoices(caoNumber, choices);
        List<String> oneUpdatedChoices = daoDriver.getCourseChoices(caoNumber);
        assertEquals(choices, oneUpdatedChoices);
    }
}