package resources;

import model.Course;
import model.Grade;
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

    public boolean deleteStudent(int index) {
        if(index<students.size())
        {
            students.remove(index);
            return true;
        }
        return false;
    }

    public Student getStudent(int index) {
        Student student = null;
        if(index<students.size())
            student = students.get(index);
        return student;
    }
    public List<Student> getStudents() {
        return instance.students;
    }

    public void fillDatalists () {
        //students.add(new Student("aaaaa", "atatata", Date.from(Instant.now())));
        //students.add(new Student("bbbbb","bybybyby", Date.from(Instant.now())));
        //students.get(0).addGrade(new Grade(3.5, Date.from(Instant.now()), new Course("WF","W.Fornalik")));

    }

}
