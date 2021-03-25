package com.dkit.gd2.kevinmcenroe.reference;

import com.dkit.gd2.johnloane.exceptions.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Adapted from sample code
public class MySqlDAO
{
    public Connection getConnection() throws DAOException
    {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/gd2_user_database_2021";
        String username = "root";
        String password  = "";

        Connection con = null;

        try
        {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
        }
        catch(ClassNotFoundException cnfe)
        {
            System.out.println("Class not found " + cnfe.getMessage());
            System.exit(1);
        }
        catch(SQLException se)
        {
            System.out.println("Connection failed " + se.getMessage());
            System.exit(2);
        }

        System.out.println("Connected successfully");
        return con;
    }

    public void freeConnection(Connection con) throws DAOException
    {
        try
        {
            if(con != null)
            {
                con.close();
                con = null;
            }
        }
        catch(SQLException se)
        {
            System.out.println("Failed to free the connection " + se.getMessage());
            System.exit(1);
        }
    }
}
