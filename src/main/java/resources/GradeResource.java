package resources;

import model.Course;
import model.Grade;
import model.Student;
import org.mongodb.morphia.Datastore;
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

@Path("students/{index}/courses/{courseId}/grades")
public class GradeResource {
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response getGrades(@PathParam("index") int index, @PathParam("courseId") int courseId) {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
        if(student.getListOfGrades()==null) { return Response.noContent().build();}
        List<Grade> allGrades = student.getListOfGrades();
        List<Grade> specificGrades = new ArrayList<>();
        for (Grade grade:allGrades) {
            if(grade.getCourse().getCourseId() == courseId)
                specificGrades.add(grade);
        }
        if(specificGrades==null) { return Response.noContent().build();}
        return Response.ok(specificGrades).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response postGrade(Grade grade, @PathParam("index") int index, @PathParam("courseId") int courseId, @Context UriInfo uriInfo) {
        if(!grade.validateValue()) { return Response.status(Response.Status.BAD_REQUEST).build(); }
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
        if(student == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
        Course course = datastore.createQuery(Course.class).field("courseId").equal(courseId).get();
        if(course == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
        //Course course = datastore.createQuery(Course.class).field("title").equal("BHP").get();
        grade.giveId();
        grade.setCourse(course);
        student.addGrade(grade);
        datastore.delete(student);////
        datastore.save(student);
        String newId = String.valueOf(grade.getGradeId());
        return Response.created(URI.create(uriInfo.getAbsolutePath().toString()+"/"+newId)).entity(grade).build();
    }

    @Path("/{gradeId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getGrade(@PathParam("index") int index, @PathParam("courseId") int courseId, @PathParam("gradeId") int gradeId) {
        //List<Grade> specificGrades = getGrades(index,courseId);// a bo sie typ odpowiedzi zmienil
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
        if(student.getListOfGrades()==null) { return Response.noContent().build();}
        List<Grade> allGrades = student.getListOfGrades();
        List<Grade> specificGrades = new ArrayList<>();
        for (Grade grade:allGrades) {
            if(grade.getCourse().getCourseId() == courseId)
                specificGrades.add(grade);
        }
        for (Grade grade:specificGrades) {
            if(grade.getGradeId()==gradeId)
                return Response.ok(grade).build();
        }
        return Response.noContent().build();
    }

    @Path("/{gradeId}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response putGrade(@PathParam("index") int index, @PathParam("courseId") int courseId, @PathParam("gradeId") int gradeId, @NotNull @Valid Grade updGrade) {
        if(!updGrade.validateValue()) { return Response.status(Response.Status.BAD_REQUEST).build(); }
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
        List<Grade> allGrades = student.getListOfGrades();
        Grade grade = null;
        for (Grade grad:allGrades) {
            if (grad.getCourse().getCourseId() == courseId && grad.getGradeId() == gradeId)
                grade = grad;
        }
        if(grade == null)
            return Response.noContent().build();
        else {
            if(updGrade.getValue() != 0) { grade.setValue(updGrade.getValue()); }
            if(updGrade.getDate() != null) { grade.setDate(updGrade.getDate()); }
        }
        datastore.delete(student);
        datastore.save(student);
        return Response.ok(grade).build();
    }

    @Path("/{gradeId}")
    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    public Response deleteGrade(@PathParam("index") int index, @PathParam("courseId") int courseId, @PathParam("gradeId") int gradeId) {
//        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
//        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
//        if(student == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
//
//        datastore.delete(student);
//        datastore.save(student);
//        String message = "Grade " + index + " deleted";
//        return Response.ok(message).build();

        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
        List<Grade> allGrades = student.getListOfGrades();
        Grade grade = null;
        for (Grade grad:allGrades) {
            if (grad.getCourse().getCourseId() == courseId && grad.getGradeId() == gradeId)
                grade = grad;
        }
        if(grade == null)
            return Response.noContent().build();
        else {
            student.getListOfGrades().remove(grade);
        }
        datastore.delete(student);
        datastore.save(student);
        String message = "Grade " + gradeId + " deleted";
        return Response.ok(message).build();
    }
}
