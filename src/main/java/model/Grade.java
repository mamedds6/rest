package model;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import utilities.DatastoreHandler;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Darek on 2017-05-04.
 */

@Entity
@XmlRootElement
public class Grade {
    private static final double[] gradingScale = new double[]{2.0, 3.0, 3.5, 4.0, 4.5, 5.0};
    //private static int counter = 1; //hehe // no bo ciezko wyciagnac max wartosc id...
    @Id
    private int id;
    private double value;
    private Date date;
    @Embedded
    private Course course;

    public Grade() { }
    public Grade(int id, double value, Date date, Course course) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.course = course;
    }
    public void giveId() {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        int maxId = datastore.find(Grade.class).order("-id").get().getId();
        id = 1 + maxId;
    }
//    public void giveId() {
//        id = counter;
//        counter++;
//    }

    public boolean validateValue() {
        for(double item:gradingScale) {
            if(item==value)
                return true;
        }
        return false;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
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
