//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.core;

import java.util.Objects;

//Adapted from CA4
public class CourseDTO {

    private String courseId;
    private String level;
    private String title;
    private String institution;

    // Copy Constructor
    public CourseDTO(CourseDTO course) {
        this.courseId = course.courseId;
        this.level = course.level;
        this.title = course.title;
        this.institution = course.institution;
    }

    // Constructor
    public CourseDTO(String courseId, String level, String title, String institution) {
        this.courseId = courseId;
        this.level = level;
        this.title = title;
        this.institution = institution;
    }

    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getInstitution() {
        return institution;
    }
    public void setInstitution(String institution) {
        this.institution = institution;
    }
    public String getTitle() { return title;  }
    public void setTitle(String title) { this.title = title; }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", level='" + level + '\'' +
                ", title='" + title + '\'' +
                ", institution='" + institution + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDTO course = (CourseDTO) o;
        return courseId.equals(course.courseId) && level.equals(course.level) && title.equals(course.title) && institution.equals(course.institution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, level, title, institution);
    }
}
