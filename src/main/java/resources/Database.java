package resources;

import model.Student;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Darek on 2017-05-05.
 */
public class Database {
    public List<Student> students;
    private List<Student> courses;
    private List<Student> grades;

    public Database() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
        grades = new ArrayList<>();
    }

    public void fillDatalists () {
        students.add(new Student(3,"mongoloid", "maximus", Date.from(Instant.now())));
        students.add(new Student(123234,"ops","drops", Date.from(Instant.now())));

    }

}
