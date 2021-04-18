//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.client;

import com.dkit.gd2.kevinmcenroe.core.CourseDTO;
import com.dkit.gd2.kevinmcenroe.core.StudentDTO;
import com.dkit.gd2.kevinmcenroe.server.*;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class DAODriverTest extends TestCase {

    public void testRegisterStudent() throws DAOException {
        StudentDTO student = new StudentDTO(87654321, "1999-01-01", "mypassword");
        IStudentDAOInterface IStudentDAO = new MySqlStudentDAO();

        IStudentDAO.registerStudent(student);

        boolean registered = IStudentDAO.isRegistered(student.getCaoNumber());
        assertTrue(registered);
    }

    public void testIsRegistered() throws DAOException {
        StudentDTO regStudent = new StudentDTO(87654321, "2000-02-02", "password2");
        IStudentDAOInterface IStudentDAO = new MySqlStudentDAO();
        IStudentDAO.registerStudent(regStudent);

        boolean registered = IStudentDAO.isRegistered(regStudent.getCaoNumber());
        boolean validLogIn = IStudentDAO.logInStudent(regStudent);
        assertEquals(registered, validLogIn);

        StudentDTO unregStudent = new StudentDTO(99999999, "1999-09-09", "password9");
        boolean unregistered = IStudentDAO.isRegistered(unregStudent.getCaoNumber());
        boolean invalidLogIn = IStudentDAO.logInStudent(unregStudent);
        assertEquals(unregistered, invalidLogIn);
    }

    public void testGetCourseByCourseID() throws DAOException {
        ICourseDAOInterface ICourseDAO = new MySqlCourseDAO();

        String courseID = "DK001";
        //The test requires that a course of this ID exist in the database
        //This courseID can be changed to match any other existing course

        CourseDTO gotCourse = ICourseDAO.getCourseByID(courseID);
        assertEquals(gotCourse.getCourseId(), courseID);
    }

    public void testLogInStudent() throws DAOException {
        StudentDTO regStudent = new StudentDTO(99999991, "2000-02-02", "password2");
        IStudentDAOInterface IStudentDAO = new MySqlStudentDAO();
        IStudentDAO.registerStudent(regStudent);

        boolean validLogIn = IStudentDAO.logInStudent(regStudent);
        assertTrue(validLogIn);

        StudentDTO unregStudent = new StudentDTO(99999992, "1999-09-09", "password9");
        boolean invalidLogIn = IStudentDAO.logInStudent(unregStudent);
        assertFalse(invalidLogIn);
    }

    public void testGetAllCourses() throws DAOException {
        //Should additional courses be added to the database, this test should be updated to reflect that
        List<CourseDTO> allCourses = new ArrayList<>();
        CourseDTO courseAA = new CourseDTO("DK001", "8", "Accounting and Finance", "DKIT");
        CourseDTO courseAB = new CourseDTO("DK002", "8", "Business Studies", "DKIT");
        CourseDTO courseAC = new CourseDTO("DK003", "8", "Computing in Games Development", "DKIT");
        CourseDTO courseAD = new CourseDTO("DK004", "8", "Computing in Software Development", "DKIT");
        CourseDTO courseAE = new CourseDTO("DK005", "8", "Augmented and Virtual Reality", "DKIT");
        CourseDTO courseAF = new CourseDTO("DK006", "8", "Mathematics and Data Science", "DKIT");
        CourseDTO courseAG = new CourseDTO("DK007", "8", "Computing Systems and Operations", "DKIT");
        CourseDTO courseAH = new CourseDTO("DK008", "8", "Building Surveying", "DKIT");
        CourseDTO courseAI = new CourseDTO("DK009", "7", "Creative Media", "DKIT");
        CourseDTO courseAJ = new CourseDTO("DK010", "7", "Music", "DKIT");

        CourseDTO courseBA = new CourseDTO("TC001", "8", "Acting", "TCD");
        CourseDTO courseBB = new CourseDTO("TC002", "8", "Ancient History", "TCD");
        CourseDTO courseBC = new CourseDTO("TC003", "8", "Business Studies and French", "TCD");
        CourseDTO courseBD = new CourseDTO("TC004", "8", "Business Studies and German", "TCD");
        CourseDTO courseBE = new CourseDTO("TC005", "8", "Business Studies and Polish", "TCD");
        CourseDTO courseBF = new CourseDTO("TC006", "8", "Classical Civilisation", "TCD");
        CourseDTO courseBG = new CourseDTO("TC007", "7", "Classical Languages", "TCD");
        CourseDTO courseBH = new CourseDTO("TC008", "7", "Clinical Speach and Language", "TCD");
        CourseDTO courseBI = new CourseDTO("TC009", "7", "English Studies", "TCD");
        CourseDTO courseBJ = new CourseDTO("TC010", "7", "German", "TCD");

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
        List<CourseDTO> gotCourses = daoDriver.getAllCourses();
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