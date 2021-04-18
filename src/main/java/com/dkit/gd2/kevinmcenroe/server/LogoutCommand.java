//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.CAOService;

public class LogoutCommand implements ICommand {
    @Override
    public String generateResponse(String[] components, DAODriver daoDriver) {
        return CAOService.SUCCESSFUL_LOGOUT;
    }
}
