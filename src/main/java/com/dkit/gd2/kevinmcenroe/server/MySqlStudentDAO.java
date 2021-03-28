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
    public void registerStudent(Student student) throws DAOException
    {
        int caoNumber = student.getCaoNumber();
        String dateOfBirth = student.getDayOfBirth();
        String password = student.getPassword();

        Connection con = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        List<Student> users = new ArrayList<>();

        try
        {
            con = this.getConnection();
            //insert into student(cao_number, date_of_birth, password) values(1, "2000-01-01", "password1");
            String query = "insert into student(cao_number, date_of_birth, password) values(" + "\"" + caoNumber +  "\",\"" + dateOfBirth + "\",\"" + password + "\")";
            preparedStatement = con.prepareStatement(query);
            result = preparedStatement.executeUpdate();
/*
            while (resultSet.next())
            {
                int gotCaoNumber = resultSet.getInt("cao_number");
                String gotDateOfBirth = resultSet.getString("date_of_birth");
                String gotPassword = resultSet.getString("password");

                Student readInUser = new Student(gotCaoNumber, gotDateOfBirth, gotPassword);
                users.add(readInUser);
            }*/
        } catch (SQLException se)
        {
            throw new DAOException("registerStudent() " + se.getMessage());
        } finally
        {
            try
            {
                if (preparedStatement != null)
                {
                    preparedStatement.close();
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
    }












    @Override
    public List<Student> findAllStudents() throws DAOException
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
                int gotCaoNumber = rs.getInt("cao_number");
                String gotDateOfBirth = rs.getString("date_of_birth");
                String gotPassword = rs.getString("password");

                Student readInUser = new Student(gotCaoNumber, gotDateOfBirth, gotPassword);
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
    public Student findStudentByCAONumberPassword(String caoNumber, String password) throws DAOException
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

                Student readInUser = new Student(gotCaoNumber, gotDateOfBirth, gotPassword);
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

