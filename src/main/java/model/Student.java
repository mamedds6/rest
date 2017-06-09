package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import resources.GradeResource;
import resources.StudentResource;
import utilities.DatastoreHandler;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Darek on 2017-05-04.
 */

@Entity("students")
@XmlRootElement
public class Student {
    @Id
    @XmlTransient
    private ObjectId id;
    @Indexed(name = "index", unique = true)
    private int index;
    private String firstName;
    private String lastName;
    @JsonFormat(shape=JsonFormat.Shape.STRING,
            pattern="yyyy-MM-dd", timezone="CET")
    private Date dateOfBirth;
    @Embedded
    private List<Grade> grades;

    @InjectLinks({
            @InjectLink(resource = StudentResource.class, rel = "parent"),
            @InjectLink(resource = StudentResource.class, method = "getStudent", rel = "self"),
            @InjectLink(resource = GradeResource.class, rel = "grades")
    })
    @XmlElement(name="link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    List<Link> links;

    public Student() {
        //this.id = new ObjectId(); //w Coursie pomogło, ale moze tu nie potrzeba
        this.grades = new ArrayList<>();
    }

    public Student(int index, String firstName, String lastName, Date dateOfBirth) {
        //this.id = new ObjectId();
        this.index = index; //giveIndex();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.grades = new ArrayList<>();
    }

    public void giveIndex() {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        int maxIndex = datastore.find(Student.class).order("-index").get().getIndex();
        index = 1 + maxIndex;
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