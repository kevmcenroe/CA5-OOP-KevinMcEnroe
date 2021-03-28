//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.Colours;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
All of the database functionality should be here. You will need a DAO for each table that you are interacting with in the database
 */

//Adapted from sample code
public class MySqlDAO
{
    public Connection getConnection() throws DAOException
    {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop_ca5_kevin_mcenroe";
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
            System.out.println(Colours.RED + "Class not found " + cnfe.getMessage() + Colours.RESET);
            System.exit(1);
        }
        catch(SQLException se)
        {
            System.out.println(Colours.RED + "Connection failed " + se.getMessage() + Colours.RESET);
            System.exit(2);
        }

        System.out.println(Colours.GREEN + "Connected successfully to the database" + Colours.RESET);
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
            System.out.println(Colours.RED + "Failed to free the connection " + se.getMessage() + Colours.RESET);
            System.exit(1);
        }
    }
}
