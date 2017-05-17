package model;

import org.mongodb.morphia.annotations.Reference;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Darek on 2017-05-04.
 */

//@Embedded ???
@XmlRootElement
public class Grade {
    private static final double[] gradingScale = new double[]{2.0, 3.0, 3.5, 4.0, 4.5, 5.0};
    private double value;
    private Date date;
    @Reference
    private Course course;

    public Grade() {}
    public Grade(double value, Date date, Course course) {
        this.value = value;
        this.date = date;
        this.course = course;
    }

    public boolean validateValue() {
        for(double item:gradingScale) {
            if(item==value)
                return true;
        }
        return false;
    }

    public double getValue() {
        return value;
    }
    public Date getDate() {
        return date;
    }
    public Course getCourse() {
        return course;
    }

    public void setValue(double value) {
        this.value = value;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
}
