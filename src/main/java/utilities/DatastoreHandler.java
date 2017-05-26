package utilities;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import model.Course;
import model.Grade;
import model.Student;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Darek on 2017-05-17.
 */
public class DatastoreHandler {
    private static DatastoreHandler instance = new DatastoreHandler();
    private Datastore datastore;

    public static DatastoreHandler getInstance() {
        return instance;
    }
    private DatastoreHandler() {
        final Morphia morphia = new Morphia();
        morphia.mapPackage("model");
        MongoClient client = new MongoClient("localhost", 8004);

        MongoDatabase db = client.getDatabase("rest");
        db.drop();

        datastore = morphia.createDatastore(client, "rest");
        datastore.ensureIndexes();
    }
    public Datastore getDatastore() {
        return datastore;
    }

    public void fillDatastore () {
        final Student studentA = new Student(1,"Andrzej", "Adamski", Date.from(Instant.parse(("1993-01-03T10:15:30.00Z"))));
        final Student studentB = new Student(2,"Barbara", "Bulwa", Date.from(Instant.parse(("1993-08-11T10:15:30.00Z"))));
        final Student studentC = new Student(3,"Cezary", "Całka", Date.from(Instant.parse(("1993-05-07T10:15:30.00Z"))));
        final Course courseA = new Course(1,"Algebra", "A.Alfonso");
        final Course courseB = new Course(2,"BHP", "B.Boruch");
        final Course courseC = new Course(3,"Chemia", "C.Curie");
        final Course courseD = new Course(4,"Databases", "D.Danielczak");
        studentA.addGrade( new Grade(3.5,Date.from(Instant.now()),courseA));
        studentA.addGrade( new Grade(4.5,Date.from(Instant.now()),courseB));
        studentB.addGrade( new Grade(2.0,Date.from(Instant.now()),courseB));
        studentB.addGrade( new Grade(4.0,Date.from(Instant.now()),courseC));
        studentC.addGrade( new Grade(5.0,Date.from(Instant.now()),courseA));
        studentC.addGrade( new Grade(3.0,Date.from(Instant.now()),courseC));
        datastore.save(studentA);
        datastore.save(studentB);
        datastore.save(studentC);
        datastore.save(courseA);
        datastore.save(courseB);
        datastore.save(courseC);
        datastore.save(courseD);
    }

    public void getGradesCounter () {
        List<Student> allStudents = datastore.find(Student.class).asList();
        for (Student stud:allStudents) {
            List<Grade> allGrades = stud.getListOfGrades();
            for (Grade grad:allGrades) {
                if(grad.getGradeId()>Grade.getCounter()) {
                    Grade.setCounter(grad.getGradeId());
                }
            }
//        List<Grade> grades = new ArrayList<>();
//        List<Student> allStudents = datastore.find(Student.class).asList();
//        for (Student stud:allStudents) {
//            grades.addAll(stud.getListOfGrades());
        }
    }
}
