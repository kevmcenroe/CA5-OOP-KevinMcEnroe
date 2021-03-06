//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.Colours;
import com.dkit.gd2.kevinmcenroe.core.CourseDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlStudentCoursesDAO extends MySqlDAO implements ICourseChoiceDAOInterface{
    @Override
    public List<String> getCourseChoicesByCAONumber(int caoNumber) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> courses = new ArrayList<>();

        try
        {
            con = this.getConnection();

            String query = "select * from student_courses where cao_number = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, Integer.toString(caoNumber));

            rs = ps.executeQuery();

            if (rs.isBeforeFirst())
            {
                while(rs.next()) {
                    String gotCourseID = rs.getString("courseid");

                    //If a course object were required at this point, this code may be useful:
                    //MySqlCourseDAO courseDAO = new MySqlCourseDAO();
                    //Course matchingCourse = courseDAO.getCourseByID(gotCourseID);
                    //courses.add(matchingCourse);

                    courses.add(gotCourseID);
                }
                return courses;
            }
            else
            {
                System.out.println(Colours.RED + "No course choices recorded (CAO Number: " + caoNumber + ")" + Colours.RESET);
                return null;
            }
        }
        catch (SQLException se)
        {
            throw new DAOException(Colours.RED + "getCourseChoicesByCAONumber() " + se.getMessage() + Colours.RESET);
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();

                if (ps != null)
                    ps.close();

                if (con != null)
                    freeConnection(con);

            } catch (SQLException se)
            {
                throw new DAOException(Colours.RED + "getCourseChoicesByCAONumber() finally " + se.getMessage() + Colours.RESET);
            }
        }
    }


    @Override
    public boolean updateCourseChoices(int caoNumber, List<String> newChoicesByID) throws DAOException {
        //System.out.println("Updating course choices by CAO number [CAO Number: " + caoNumber + "]...");

        try
        {
            MySqlStudentDAO studentDAO = new MySqlStudentDAO();
            MySqlCourseDAO courseDAO = new MySqlCourseDAO();

            if(studentDAO.isRegistered(caoNumber)) {
                clearChoicesByCAONumber(caoNumber);

                for (String courseID : newChoicesByID) {
                    CourseDTO course = courseDAO.getCourseByID(courseID);
                    if (course != null) {
                        insertCourseChoiceByID(caoNumber, courseID);
                    } else {
                        System.out.println(Colours.RED + "Course choice discarded due to invalid courseID (CourseID: " + courseID + ")" + Colours.RESET);
                    }
                }

                List<String> recordedChoices = getCourseChoicesByCAONumber(caoNumber);
                for(int i=0; i<recordedChoices.size(); i++){
                    if(recordedChoices.get(i).equals(newChoicesByID.get(i)))
                    {
                        System.out.println(Colours.GREEN + newChoicesByID.get(i) + " successfully inserted" + Colours.RESET);
                    }
                    else
                    {
                        System.out.println(Colours.RED + "Unsuccessful insert" + Colours.RESET);
                        return false;
                    }
                }
                return true;
            }
            else
            {
                System.out.println(Colours.RED + "Student is not registered (CAO Number: " + caoNumber + ")" + Colours.RESET);
                return false;
            }
        }
        catch (SQLException se)
        {
            throw new DAOException(Colours.RED + "updateCourseChoices() - " + se.getMessage() + Colours.RESET);
        }
    }

    private void insertCourseChoiceByID(int caoNumber, String courseID) throws DAOException{

        Connection con = null;
        PreparedStatement ps = null;

        try
        {
            con = this.getConnection();

            System.out.println("Inserting course choice [CAO Number: " + caoNumber + ", Course ID: " + courseID + "]...");
            String query = "insert into student_courses(cao_number, courseid) values(?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, Integer.toString(caoNumber));
            ps.setString(2, courseID);
            ps.executeUpdate();
        }
        catch (SQLException se)
        {
            throw new DAOException(Colours.RED + "insertCourseChoiceByID() - " + se.getMessage() + Colours.RESET);
        }
        finally
        {
            try
            {
                if (ps != null)
                    ps.close();

                if (con != null)
                    freeConnection(con);
            }
            catch (SQLException se)
            {
                throw new DAOException(Colours.RED + "insertCourseChoiceByID() finally - " + se.getMessage() + Colours.RESET);
            }
        }
    }

    private void clearChoicesByCAONumber(int caoNumber) throws DAOException{
        System.out.println("Clearing existing course choices [CAO Number: " + caoNumber + "]...");

        Connection con = null;
        PreparedStatement ps = null;

        try
        {
            con = this.getConnection();
            String query = "delete from student_courses where cao_number = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, Integer.toString(caoNumber));
            ps.executeUpdate();

        }
        catch (SQLException se)
        {
            throw new DAOException(Colours.RED + "clearChoicesByCAONumber() - " + se.getMessage() + Colours.RESET);
        }
        finally
        {
            try
            {
                if (ps != null)
                    ps.close();

                if (con != null)
                    freeConnection(con);
            }
            catch (SQLException se)
            {
                throw new DAOException(Colours.RED + "clearChoicesByCAONumber() finally - " + se.getMessage() + Colours.RESET);
            }
        }
    }
}
