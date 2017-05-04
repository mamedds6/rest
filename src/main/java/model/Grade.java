package model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Darek on 2017-05-04.
 */

@XmlRootElement
public class Grade {
    private float value;
    private Date date;
    private Course course;

    public Grade() {}

    public float getValue() {
        return value;
    }
    public Date getDate() {
        return date;
    }
    public Course getCourse() {
        return course;
    }

    public void setValue(float value) {
        this.value = value;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
}
