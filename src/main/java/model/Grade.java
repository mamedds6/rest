package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.Instant;
import java.util.Date;

/**
 * Created by Darek on 2017-05-04.
 */

@Embedded
@XmlRootElement
public class Grade {
    private static final double[] gradingScale = new double[]{2.0, 3.0, 3.5, 4.0, 4.5, 5.0};
    private static int counter = 1; //hehe // no bo ciezko wyciagnac max wartosc gradeId...
    private int gradeId;
    private double value;
    @JsonFormat(shape=JsonFormat.Shape.STRING,
            pattern="yyyy-MM-dd", timezone="CET")
    private Date date;
    @Reference
    private Course course;

    public Grade() {
        date = Date.from(Instant.now());
    }
    public Grade(double value, Date date, Course course) {
        giveId();
        this.value = value;
        this.date = date;
        this.course = course;
    }
    public void giveId() {
        gradeId = counter;
        counter++;
    }

    public boolean validateValue() {
        for(double item:gradingScale) {
            if(item==value)
                return true;
        }
        return false;
    }

    public int getGradeId() {
        return gradeId;
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

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
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
