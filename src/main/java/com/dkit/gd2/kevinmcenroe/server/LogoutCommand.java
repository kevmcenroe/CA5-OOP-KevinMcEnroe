package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.CAOService;
import com.dkit.gd2.kevinmcenroe.core.Student;

public class LogoutCommand implements ICommand {
    @Override
    public String generateResponse(String[] components, DAODriver daoDriver) {
        return CAOService.LOGOUT_COMMAND;
    }
}
