//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.CAOService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateChoicesCommand implements ICommand {
    @Override
    public String generateResponse(String[] components, DAODriver daoDriver) {
        int caoNumber = Integer.parseInt(components[1]);
        List<String> choices = new ArrayList<>();

        if(components.length > 2)
        {
            choices.addAll(Arrays.asList(components).subList(2, components.length));
        }

        if(choices.size() > 0) {
            if(!daoDriver.updateCourseChoices(caoNumber, choices))
                return CAOService.FAILED_UPDATE_CHOICES;
            else
                return CAOService.SUCCESSFUL_UPDATE_CHOICES;
        }
        else
            return CAOService.FAILED_UPDATE_CHOICES;
    }
}
