//Kevin McEnroe D00242092
package com.dkit.gd2.kevinmcenroe.core;

import java.util.Objects;

////Adapted from CA4
public class StudentDTO {
    private int caoNumber;
    private String dateOfBirth;
    private String password;

    public StudentDTO(StudentDTO student) {
        this.caoNumber = student.getCaoNumber();
        this.dateOfBirth = student.getDayOfBirth();
        this.password = student.getPassword();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDTO student = (StudentDTO) o;
        return caoNumber == student.caoNumber && dateOfBirth.equals(student.dateOfBirth) && password.equals(student.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caoNumber, dateOfBirth, password);
    }

    // Constructor
    public StudentDTO(int caoNumber, String dateOfBirth, String password) {
        this.caoNumber = caoNumber;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
    }

    public int getCaoNumber() {
        return caoNumber;
    }

    public void setCaoNumber(int caoNumber) {
        this.caoNumber = caoNumber;
    }

    public String getDayOfBirth() {
        return dateOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dateOfBirth = dayOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{" +
                "caoNumber=" + caoNumber +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
