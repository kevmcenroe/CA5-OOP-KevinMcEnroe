package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.CAOService;
import com.dkit.gd2.kevinmcenroe.core.Colours;
import com.dkit.gd2.kevinmcenroe.core.Student;

public class LoginCommand implements ICommand {
    @Override
    public String generateResponse(String[] components, DAODriver daoDriver) {

        int caoNumber = Integer.parseInt(components[1]);
        String dateOfBirth = components[2];
        String password = components[3];
        Student student = new Student(caoNumber, dateOfBirth, password);

        if(daoDriver.logIn(student))
            return CAOService.SUCCESSFUL_LOGIN;
        else
            return CAOService.FAILED_LOGIN;
    }
}
