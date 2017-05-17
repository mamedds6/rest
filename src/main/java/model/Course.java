package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Darek on 2017-05-04.
 */

@Entity
@XmlRootElement
public class Course {
    @Id
    private ObjectId id;
    private String title;
    private String instructor;

    public Course() {}
    public Course(String title, String instructor) {
        this.title = title;
        this.instructor = instructor;
    }

    public String getTitle() {
        return title;
    }
    public String getInstructor() {
        return instructor;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
