package model;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Darek on 2017-05-04.
 */

@XmlRootElement
public class Student {
    private int index;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private List<Grade> grades;

    public Student() {
        this.grades = new ArrayList<>();
    }

    public Student(int index, String firstName, String lastName, Date dateOfBirth) {
        this.index = index;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.grades = new ArrayList<>();
    }

    public int getIndex() {
        return index;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public List<Grade> getListOfGrades() {
        return grades;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public void setListOfGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }
}
