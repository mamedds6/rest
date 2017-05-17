package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import utilities.ObjectIdJaxbAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by Darek on 2017-05-04.
 */

@Entity
@XmlRootElement
public class Course {
    @Id
    //@XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    private ObjectId id;
    //@Indexed(name = "title", unique = true)
    private String title;
    private String instructor;

    public Course() {this.id = new ObjectId();}
    public Course(String title, String instructor) {
        this.id = new ObjectId();
        this.title = title;
        this.instructor = instructor;
    }

    @XmlTransient
    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
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
