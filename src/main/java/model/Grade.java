package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.time.Instant;
import java.util.Date;

/**
 * Created by Darek on 2017-05-04.
 */

@Embedded
@XmlRootElement
public class Grade {
    private static final double[] gradingScale = new double[]{2.0, 3.0, 3.5, 4.0, 4.5, 5.0};
    private static int counter = 0;
    private int gradeId;
    private double value;
    @JsonFormat(shape=JsonFormat.Shape.STRING,
            pattern="yyyy-MM-dd", timezone="CET")
    private Date date;
    //@XmlTransient
    //private int courseId;
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
        //this.courseId = course.getCourseId(); //śmiesznie ale to jest chyba tylko uzyta przy fillDatastore so...
    }
    public void giveId() {
        counter++;
        gradeId = counter;
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
//    public int getCourseId() {
//        return courseId;
//    }

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
//    public void setCourseId(int courseId) {
//        this.courseId = courseId;
//    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Grade.counter = counter;
    }
}
