package resources;

import model.Grade;
import model.Student;
import model.Course;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import utilities.DatastoreHandler;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darek on 2017-05-04.
 */

@Path("courses")
public class CourseResource {
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Course> getCourses() {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        List<Course> courses = datastore.find(Course.class).order("courseId").asList();
        return courses;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response postCourse( Course course, @Context UriInfo uriInfo) {
        course.giveCourseId();
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        datastore.save(course);
        String newIndex = String.valueOf(course.getCourseId());
        return Response.created(URI.create(uriInfo.getAbsolutePath().toString()+"/"+newIndex)).entity(course).build();
    }

    @Path("/{courseId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCourse(@PathParam("courseId") int courseId) {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Course course = datastore.createQuery(Course.class).field("courseId").equal(courseId).get();
        if(course == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
        return Response.ok(course).build();
    }

    @Path("/{courseId}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response putCourse(@PathParam("courseId") int courseId,@NotNull @Valid Course updCourse) {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Course course = datastore.createQuery(Course.class).field("courseId").equal(courseId).get();
        if(course == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
//        if(updCourse.getTitle() != null) { course.setTitle(updCourse.getTitle()); }
//        if(updCourse.getInstructor() != null) { course.setInstructor(updCourse.getInstructor()); }
        course.setTitle(updCourse.getTitle());
        course.setInstructor(updCourse.getInstructor());

        datastore.delete(course);
        datastore.save(course);
        return Response.ok(course).build();
    }

    @Path("/{courseId}")
    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    public Response deleteCourse(@PathParam("courseId") int courseId) {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Course course = datastore.createQuery(Course.class).field("courseId").equal(courseId).get();
        if(course == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }

        List<Student> allStudents = datastore.find(Student.class).asList();
        for (Student stud:allStudents) {
            List<Grade> allGrades = stud.getListOfGrades();
            if(allGrades==null) continue;
            for (Grade grad:allGrades) {
                if(grad.getCourseId() == courseId)
                {
                    stud.getListOfGrades().remove(grad);
                    datastore.delete(stud);
                    datastore.save(stud);
                }
            }
        }
        datastore.delete(course);
//
//        List<Student> allStudents2 = datastore.find(Student.class).asList();
//        for (Student stud:allStudents2) {
//            List<Grade> allGrades = stud.getListOfGrades();
//            if(allGrades==null) continue;
//            for (Grade grad:allGrades) {
//                if(grad.getCourse().getCourseId() == courseId)
//                {
//                    stud.getListOfGrades().remove(grad);
//                    datastore.delete(stud);
//                    datastore.save(stud);
//                }
//            }
//        }
//        datastore.delete(course);
//
//        List<Student> allStudents3 = datastore.find(Student.class).asList();
//        for (Student stud:allStudents3) {
//            List<Grade> allGrades = stud.getListOfGrades();
//            if(allGrades==null) continue;
//            for (Grade grad:allGrades) {
//                if(grad.getCourse().getCourseId() == courseId)
//                {
//                    stud.getListOfGrades().remove(grad);
//                    datastore.delete(stud);
//                    datastore.save(stud);
//                }
//            }
//        }
//        datastore.delete(course);
        String message = "Course " + course.getTitle() + " deleted";
        return Response.ok(message).build();
    }

    //stary students/1/courses/1/grades
//    @GET
//    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
//    public Response getGrades(@PathParam("index") int index, @PathParam("courseId") int courseId) {
//        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
//        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
//        if(student.getListOfGrades()==null) { return Response.noContent().build();}
//        List<Grade> allGrades = student.getListOfGrades();
//        List<Grade> specificGrades = new ArrayList<>();
//        for (Grade grade:allGrades) {
//            if(grade.getCourse().getCourseId() == courseId)
//                specificGrades.add(grade);
//        }
//        if(specificGrades==null) { return Response.noContent().build();}
//        return Response.ok(specificGrades).build();
//    }

    @Path("/{courseId}/grades") ///// DO POPRAWIENIA
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response getCoursesGrades(@PathParam("courseId") int courseId) { ///zmienic z response na List<>
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Course course = datastore.createQuery(Course.class).field("courseId").equal(courseId).get();
        if(course == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }

        List<Grade> grades = new ArrayList<>();
        List<Student> allStudents = datastore.find(Student.class).asList();
        for (Student stud:allStudents) {
            List<Grade> allGrades = stud.getListOfGrades();
            for (Grade grad:allGrades) {
                if(grad.getCourse().getCourseId() == courseId)
                {
                    grades.add(grad);
                }
            }
        }
        return Response.ok(grades).build();
    }
}
