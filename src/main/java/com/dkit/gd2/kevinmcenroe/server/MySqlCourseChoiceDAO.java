//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.Colours;
import com.dkit.gd2.kevinmcenroe.core.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCourseChoiceDAO extends MySqlDAO implements ICourseChoiceDAOInterface{
    @Override
    public List<Course> getCourseChoicesByCAONumber(String caoNumber) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Course> courses = new ArrayList<>();

        try
        {
            con = this.getConnection();

            String query = "select * from student_courses where cao_number = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, caoNumber);

            rs = ps.executeQuery();

            if (rs.isBeforeFirst())
            {
                while(rs.next()) {
                    String gotCourseID = rs.getString("courseid");
                    String gotLevel = rs.getString("level");
                    String gotTitle = rs.getString("title");
                    String gotInstitution = rs.getString("institution");

                    Course matchingCourse = new Course(gotCourseID, gotLevel, gotTitle, gotInstitution);
                    System.out.println(Colours.GREEN + matchingCourse + Colours.RESET);
                    courses.add(matchingCourse);
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
    public boolean updateCourseChoices(String caoNumber) throws DAOException {
        return false;
    }
}
