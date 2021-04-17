package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.CAOService;
import com.dkit.gd2.kevinmcenroe.core.Course;

import java.util.ArrayList;
import java.util.List;

public class UpdateChoicesCommand implements ICommand {
    @Override
    public String generateResponse(String[] components, DAODriver daoDriver) {
        //boolean choicesUpdated = daoDriver.updateCourseChoices();
        int caoNumber = Integer.parseInt(components[1]);
        List<String> choices = new ArrayList<>();

        if(components.length > 2)
        {
            for (int i = 2; i < components.length; i++)
                choices.add(components[i]);
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
