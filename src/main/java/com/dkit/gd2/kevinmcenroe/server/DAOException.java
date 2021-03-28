//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import java.sql.SQLException;

public class DAOException extends SQLException
{
    public DAOException()
    {

    }

    public DAOException(String aMessage)
    {
        super(aMessage);
    }
}
