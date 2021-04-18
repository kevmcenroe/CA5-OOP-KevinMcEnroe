//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.Colours;
import com.dkit.gd2.kevinmcenroe.core.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Adapted from sample code
public class MySqlStudentDAO extends MySqlDAO implements IStudentDAOInterface
{
    @Override
    public boolean registerStudent(Student student) throws DAOException {
        int caoNumber = student.getCaoNumber();
        String dateOfBirth = student.getDayOfBirth();
        String password = student.getPassword();

        Connection con = null;
        PreparedStatement ps = null;

        try
        {
            if(!isRegistered(caoNumber)) {
                con = this.getConnection();

                String query = "insert into student(cao_number, date_of_birth, password) values(?, ?, ?)";
                ps = con.prepareStatement(query);
                ps.setString(1, Integer.toString(caoNumber));
                ps.setString(2, dateOfBirth);
                ps.setString(3, password);
                ps.executeUpdate();

                return isRegistered(caoNumber);
            }
            else
            {
                System.out.println(Colours.RED + "Student is already registered (CAO Number: " + caoNumber + ")" + Colours.RESET);
                return false;
            }
        }
        catch (SQLException se)
        {
            throw new DAOException(Colours.RED + "registerStudent() - " + se.getMessage() + Colours.RESET);
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
                throw new DAOException(Colours.RED + "registerStudent() finally - " + se.getMessage() + Colours.RESET);
            }
        }
    }

    @Override
    public boolean isRegistered(int caoNumber) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs;

        try
        {
            con = this.getConnection();

            String query = "select * from student where cao_number = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, Integer.toString(caoNumber));
            rs = ps.executeQuery();

            return rs.next();
        }
        catch (SQLException se)
        {
            throw new DAOException(Colours.RED + "isRegistered() - " + se.getMessage() + Colours.RESET);
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
                throw new DAOException(Colours.RED + "isRegistered() finally - " + se.getMessage() + Colours.RESET);
            }
        }
    }

    @Override
    public boolean logInStudent(Student student) throws DAOException {
        boolean invalidDetails = false;

        int caoNumber = student.getCaoNumber();
        String dateOfBirth = student.getDayOfBirth();
        String password = student.getPassword();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            con = this.getConnection();

            String query = "select * from student where cao_number = ? and date_of_birth = ? and password = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, Integer.toString(caoNumber));
            ps.setString(2, dateOfBirth);
            ps.setString(3, password);
            rs = ps.executeQuery();

            if (rs.next())
            {
                System.out.println(Colours.GREEN + "Logged in successfully (CAO Number: " +caoNumber + ")" + Colours.RESET);
                return true;
            }
            else
            {
                invalidDetails = true;
                //System.out.println(Colours.RED + "Invalid log in details" + Colours.RESET);
                return false;
            }
        }
        catch (SQLException se)
        {
            throw new DAOException(Colours.RED + "logInStudent() " + se.getMessage() + Colours.RESET);
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
                throw new DAOException(Colours.RED + "logInStudent() finally " + se.getMessage() + Colours.RESET);
            }
        }
    }
}

