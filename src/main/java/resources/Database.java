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
    private static Database instance;
    public List<Student> students;
    private List<Student> courses;
    private List<Student> grades;

    public Database() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
        grades = new ArrayList<>();
    }

    public static Database getInstance(){
        if(instance==null)
            instance = new Database();
        return instance;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Student getStudent(int index) {
        Student student = students.get(index);
        return student;
    }

    public void fillDatalists () {
        students.add(new Student(3,"adada", "ad", Date.from(Instant.now())));
        students.add(new Student(123234,"ops","drops", Date.from(Instant.now())));

    }

}
