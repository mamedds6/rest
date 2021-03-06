package model;

import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import resources.CourseResource;
import resources.GradeResource;
import resources.StudentResource;
import utilities.DatastoreHandler;
import utilities.ObjectIdJaxbAdapter;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * Created by Darek on 2017-05-04.
 */

@Entity("courses")
@XmlRootElement
public class Course {
    @Id
    //@XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class) //ogarnac to moze, zeby nie bylo dodatkowego id
    private ObjectId id;
    @Indexed(name = "courseId", unique = true)
    private int courseId;
    private String title;
    private String instructor;

    @InjectLinks({
            @InjectLink(resource = CourseResource.class, rel = "parent"),
            @InjectLink(resource = CourseResource.class, method = "getCourse", rel = "self")
    })
    @XmlElement(name="link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    List<Link> links;

    public Course() {
        this.id = new ObjectId();
    }
    public Course(int courseId) {
        this.id = new ObjectId();
        this.courseId = courseId;
    }
    public Course(int courseId, String title, String instructor) {
        this.id = new ObjectId();
        this.courseId = courseId;
        this.title = title;
        this.instructor = instructor;
    }

    public void giveCourseId() {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        int maxId = datastore.find(Course.class).order("-courseId").get().getCourseId();
        //wywala sie jak nie ma wczesniej kursu w bazie
        courseId = 1 + maxId;
    }

    @XmlTransient
    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }
    public String getTitle() {
        return title;
    }
    public String getInstructor() {
        return instructor;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
