//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

public interface ICommand
{
    public String generateResponse(String[] components, DAODriver daoDriver);
}
