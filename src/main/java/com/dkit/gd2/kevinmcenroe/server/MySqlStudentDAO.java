package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.Student;
import com.dkit.gd2.kevinmcenroe.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Adapted from sample code
public class MySqlStudentDAO extends MySqlDAO implements IStudentDAOInterface
{

    @Override
    public List<Student> findAllUsers() throws DAOException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Student> users = new ArrayList<>();

        try
        {
            con = this.getConnection();
            String query = "select * from student";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next())
            {
                int caoNumber = rs.getInt("cao_number");
                String dateOfBirth = rs.getString("date_of_birth");
                String pass = rs.getString("password");
                String email = rs.getString("email");

                Student readInUser = new Student(caoNumber, dateOfBirth, pass, email);
                users.add(readInUser);
            }
        } catch (SQLException se)
        {
            throw new DAOException("findAllUsers() " + se.getMessage());
        } finally
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
                throw new DAOException("findAllUsers() finally " + se.getMessage());
            }
        }
        return users;
    }

    // Utilising reference material provided
    @Override
    public Student findUserByCAONumberPassword(String caoNumber, String password) throws DAOException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Student returnedUser = null;

        try
        {
            con = this.getConnection();

            String query = "select * from student where cao_number = ? and password = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, caoNumber);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next())
            {
                int gotCaoNumber = rs.getInt("cao_number");
                String gotDateOfBirth = rs.getString("date_of_birth");
                String gotPassword = rs.getString("password");
                String gotEmail = rs.getString("email");

                Student readInUser = new Student(gotCaoNumber, gotDateOfBirth, gotPassword, gotEmail);
            }
        }
        catch (SQLException se)
        {
            throw new DAOException("findAllUsers() " + se.getMessage());
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
                throw new DAOException("findAllUsers() finally " + se.getMessage());
            }
        }
        return returnedUser;
    }
}

