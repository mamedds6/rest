package model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Darek on 2017-05-04.
 */

@XmlRootElement
public class Course {
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
