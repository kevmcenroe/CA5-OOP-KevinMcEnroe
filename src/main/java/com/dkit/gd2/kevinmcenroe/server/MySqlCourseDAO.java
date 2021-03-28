package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.Colours;
import com.dkit.gd2.kevinmcenroe.core.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCourseDAO extends MySqlDAO implements ICourseDAOInterface{
    @Override
    public Course getCourseByID(String courseID) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Course> courses = new ArrayList<>();

        try
        {
            con = this.getConnection();

            String query = "select * from course where courseid = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, courseID);

            rs = ps.executeQuery();

            if (rs.next())
            {
                String gotCourseID = rs.getString("courseid");
                String gotLevel = rs.getString("level");
                String gotTitle = rs.getString("title");
                String gotInstitution = rs.getString("institution");

                Course matchingCourse = new Course(gotCourseID, gotLevel, gotTitle, gotInstitution);
                System.out.println(Colours.GREEN + matchingCourse + Colours.RESET);
                return matchingCourse;
            }
            else
            {
                System.out.println(Colours.RED + "A course of courseID " + courseID + " does not exist" + Colours.RESET);
                return null;
            }
        }
        catch (SQLException se)
        {
            throw new DAOException(Colours.RED + "displayCourseByID() " + se.getMessage() + Colours.RESET);
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException se)
            {
                throw new DAOException(Colours.RED + "displayCourseByID() finally " + se.getMessage() + Colours.RESET);
            }
        }
    }

    @Override
    public List<Course> getAllCourses() throws DAOException {
        return null;
    }

    @Override
    public Course getCourseByCAONumber(int caoNumber) throws DAOException {
        return null;
    }
}