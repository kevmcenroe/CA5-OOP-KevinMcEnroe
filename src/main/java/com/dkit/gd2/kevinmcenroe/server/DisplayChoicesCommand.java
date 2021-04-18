//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.server;

import com.dkit.gd2.kevinmcenroe.core.CAOService;

import java.util.List;

public class DisplayChoicesCommand implements ICommand {
    @Override
    public String generateResponse(String[] components, DAODriver daoDriver) {
        List<String> gotChoices = daoDriver.getCourseChoices(Integer.parseInt(components[1]));
        if (gotChoices == null)
            return CAOService.FAILED_DISPLAY_CHOICES;
        else {
            //This approach was taken for modularity and ease of editing responses
            StringBuilder response = new StringBuilder();
            response.append(CAOService.SUCCESSFUL_DISPLAY_CHOICES);
            response.append(CAOService.BREAKING_CHARACTER);

            for(String courseID : gotChoices)
            {
                response.append(courseID);
                response.append(CAOService.BREAKING_CHARACTER);
            }

            return response.toString();
        }
    }
}
