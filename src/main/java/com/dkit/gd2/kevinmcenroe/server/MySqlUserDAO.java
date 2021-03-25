package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.johnloane.dto.User;
import com.dkit.gd2.johnloane.exceptions.DAOException;
import com.dkit.gd2.kevinmcenroe.reference.MySqlDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Adapted from sample code
public class MySqlUserDAO extends MySqlDAO implements IUserDAOInterface
{

    @Override
    public List<User> findAllUsers() throws DAOException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try
        {
            con = this.getConnection();
            String query = "select * from user";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next())
            {
                int userId = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String username = rs.getString("username");
                String password = rs.getString("password");

                User readInUser = new User(userId, firstName, lastName, username, password);
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

    @Override
    public User findUserByUsernamePassword(String username, String password) throws DAOException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User returnedUser = null;

        try
        {
            con = this.getConnection();

            String query = "select * from user where username = ? and password = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next())
            {
                int userId = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String dbusername = rs.getString("username");
                String dbpassword = rs.getString("password");

                User readInUser = new User(userId, firstName, lastName, dbusername, dbpassword);
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

