package model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created by Darek on 2017-05-04.
 */

public @XmlRootElement
class Student {
    private int index;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private List<Grade> grades;

    public Student() {}

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
}
