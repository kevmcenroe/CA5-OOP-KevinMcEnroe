package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.johnloane.dto.User;
import com.dkit.gd2.johnloane.exceptions.DAOException;

import java.util.List;

//Adapted from sample code
public interface IUserDAOInterface
{
    public List<User> findAllUsers() throws DAOException;
    public User findUserByUsernamePassword(String username, String password) throws DAOException;
}
