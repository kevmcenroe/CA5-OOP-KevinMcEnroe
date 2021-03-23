// Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.core;

import java.io.*;
import java.util.*;

/**
 * CoursesManager
 * This software component Encapsulates the storage and management of
 * all courses available through the CAO system.
 * Only administrators would typically be allowed to update this data,
 * but other users can get a COPY of all the courses by calling getAllCourses().
 * The Web Client would need this data to display the course codes,
 * course titles and institutions to the student.
 */

public class CourseManager {

    HashMap<String, Course> coursesMap = new HashMap<>();

    // Constructor

    // NOTE - Instead of reading from file, maybe read from the database here

    /*public CourseManager() {
        loadCoursesFromFile(this.coursesMap, "courses.dat");
    }
*/

    // Adapted from my StudentManager
    protected void loadCoursesFromFile(Map<String, Course> courseMap, String fileToLoad){

        try(Scanner coursesFile = new Scanner(new BufferedReader(new FileReader(fileToLoad))))
        {
            String input;
            //System.out.println("Reading courses from file...");
            while(coursesFile.hasNextLine())
            {
                input = coursesFile.nextLine();
                String[] data = input.split(",");
                String courseID = data[0];
                String level = data[1];
                String title = data[2];
                String institution = data[3];

                Course readCourse = new Course(courseID, level, title, institution);
                courseMap.put(readCourse.getCourseId(), readCourse);
                //System.out.println(IColours.GREEN + "Course added to the courses map. Course ID number: " + readCourse.getCourseId() + IColours.RESET);
            }
        }
        catch(FileNotFoundException fne)
        {
            System.out.println("Unable to read file (FileNotFoundException)");
        }
        catch(NumberFormatException nfe)
        {
            System.out.println("Input data type does not match that required (NumberFormatException)");
        }
    }

    private boolean isRegistered(Course courseToCheck){
        return this.coursesMap.containsKey(courseToCheck.getCourseId());
    }

    private Course getCourse(String courseID) {
        Course matchingCourse = this.coursesMap.get(courseID);

        return new Course(matchingCourse);
    }

    public void displayCourse(){
        String inputCourseID = getInput("Course ID");
        if(coursesMap.containsKey(inputCourseID))
            System.out.println(Colours.GREEN + getCourse(inputCourseID) + Colours.RESET);
        else
            System.out.println(Colours.RED + "A course of ID " + inputCourseID + " does not exist in the course map" + Colours.RESET);
    }

    public List<Course> getAllCourses(){
        List<Course> allCourses = new ArrayList<>();

        for (Map.Entry<String, Course> stringCourseEntry : this.coursesMap.entrySet()) {
            Course course = (Course) ((Map.Entry) stringCourseEntry).getValue();
            allCourses.add(course);
        }

        return allCourses;
    }

    // This code is similar to displayAllCourses in CourseChoicesManager but included by necessity of the brief
    public void displayAllCourses(){
        List<Course> allCourses = getAllCourses();
        for(Course course : allCourses){
            System.out.println(Colours.GREEN + course + Colours.RESET);
        }
    }

    private void addCourse(Course courseToAdd) {
        if(isRegistered(courseToAdd)){
            System.out.println(Colours.RED + "A course of course ID " + courseToAdd.getCourseId() + " already exists in the courses map" + Colours.RESET);
        }
        else{
            Course courseClone = new Course(courseToAdd);
            this.coursesMap.put(courseClone.getCourseId(), courseClone);
            System.out.println(Colours.GREEN + "Added " + courseClone + Colours.RESET);
            writeToFile(coursesMap, "courses.dat");
        }
    }

    public void displayAddCourse(){
        String courseID = getInput("Course ID");
        String level = getInput("Level");
        String title = getInput("Title");
        String institution = getInput("Institution");

        Course generatedCourse = new Course(courseID, level, title, institution);
        addCourse(generatedCourse);
    }

    private void removeCourse(String courseID) {
        this.coursesMap.remove(courseID);
        writeToFile(coursesMap, "courses.dat");
    }

    public void displayRemoveCourse(){
        System.out.println("Enter the ID of the course you wish to remove");
        printAvailableCourses();
        String courseID = getInput("Course ID");

        if(coursesMap.containsKey(courseID)){
            System.out.println(Colours.GREEN + "Removed "+ coursesMap.get(courseID) + Colours.RESET);
            removeCourse(courseID);
        }
        else
            System.out.println(Colours.RED + "Unable to find course of ID " + courseID + Colours.RESET);
    }

    private void printAvailableCourses(){
        String availableCourseCodes = getSortedKeys(coursesMap);
        System.out.println(Colours.BLUE + "Available Courses: " + availableCourseCodes + "\n" + Colours.RESET);
    }

    private String getSortedKeys(HashMap mapToSort)
    {
        ArrayList<String> sortedKeys = new ArrayList<String>(mapToSort.keySet());
        Collections.sort(sortedKeys);

        StringBuilder keyList = new StringBuilder("[");
        int index = 0;
        for (String key : sortedKeys) {
            if(index != sortedKeys.size()-1)
                keyList.append(key).append(", ");
            else
                keyList.append(key);
            index++;
        }
        keyList.append("]");

        return keyList.toString();
    }

    protected void writeToFile(Map<String, Course> courseMap, String writeFile)
    {
        try(BufferedWriter coursesFile = new BufferedWriter(new FileWriter(writeFile))) {
            for(Map.Entry<String, Course> entry : courseMap.entrySet())
            {
                coursesFile.write(entry.getValue().getCourseId() + "," + entry.getValue().getLevel() + "," + entry.getValue().getTitle() + "," + entry.getValue().getInstitution() + "\n");
            }
            /*First approach:
            Iterator courseIterator = this.coursesMap.entrySet().iterator();

            while (courseIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry)courseIterator.next();
                Course course = (Course)mapElement.getValue();
                coursesFile.write(course.getCourseId() + "," + course.getLevel() + "," + course.getTitle() + "," + course.getInstitution() +"\n");
            }*/
        }
        catch(IOException ioe)
        {
            System.out.println(Colours.RED + "Could not write to file (IOException)" + Colours.RESET);
        }
    }

    private static final Scanner keyboard = new Scanner(System.in);

    //Adapted from my CA3 submission
    private String getInput(String requested) {
        String input;
        System.out.print("Please enter " + requested + " :>");

        input = keyboard.nextLine();
        return input;
    }
}







