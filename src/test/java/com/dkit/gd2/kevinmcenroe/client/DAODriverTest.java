package com.dkit.gd2.kevinmcenroe.client;

import com.dkit.gd2.kevinmcenroe.core.Course;
import com.dkit.gd2.kevinmcenroe.core.Student;
import com.dkit.gd2.kevinmcenroe.server.*;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class DAODriverTest extends TestCase {

    /*
        Student DAO Interaction
        //Student DAO Interaction
        isRegistered(IStudentDAO, sampleStudent.getCaoNumber());
        registerStudent(IStudentDAO, sampleStudent);
        logIn(IStudentDAO, sampleStudent);
        //isRegistered(IStudentDAO, sampleStudent.getCaoNumber());
        //registerStudent(IStudentDAO, sampleStudent);
        //logIn(IStudentDAO, sampleStudent);

        //Course DAO Interaction
        //ICourseDAOInterface ICourseDAO = new MySqlCourseDAO();
        //getCourseByCourseID(ICourseDAO, "DK001");
        //getAllCourses(ICourseDAO);
        Course DAO Interaction
        ICourseDAOInterface ICourseDAO = new MySqlCourseDAO();
        getCourseByCourseID(ICourseDAO, "DK001");
        getAllCourses(ICourseDAO);
        //CourseChoice DAO Interaction
        ICourseChoiceDAOInterface ICourseChoiceDAO = new MySqlCourseChoiceDAO();
@@ -50,6 +51,8 @@ public void main( String[] args )
        choices.add("DK004");
        choices.add("DK005");
        updateCourseChoices(ICourseChoiceDAO, 109, choicesB);
 */

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

        String courseID = "TEST001";
        Course course = new Course(courseID, "8", "Testing and QA", "DKIT");
        //The test requires that this course be manually inserted into the database (as has been done)
        //This is done to streamline the test method, reduce number of moving parts and thereby error probability

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

        Course courseCA = new Course("TEST001", "8", "Testing and QA", "DKIT");

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

        allCourses.add(courseCA);

        ICourseDAOInterface ICourseDAO = new MySqlCourseDAO();
        List<Course> gotCourses = ICourseDAO.getAllCourses();
        boolean equals = allCourses.equals(gotCourses);

        assertTrue(equals);
    }

    public void testGetAllCourseIDs() {
    }

    public void testGetCourseChoices() {
    }

    public void testUpdateCourseChoices() {
    }
}