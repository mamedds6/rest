package resources;

import utilities.DatastoreHandler;
import model.Grade;
import model.Student;
import org.mongodb.morphia.Datastore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Created by Darek on 2017-05-04.
 */

@Path("students")
public class StudentResource {
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Student> getStudents() {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        List<Student> students = datastore.find(Student.class).order("index").asList();
        return students;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response postStudent( Student student, @Context UriInfo uriInfo) {
        student.giveIndex();
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        datastore.save(student);
        String newIndex = String.valueOf(student.getIndex());
        return Response.created(URI.create(uriInfo.getAbsolutePath().toString()+"/"+newIndex)).entity(student).build();
    }

    @Path("/{index}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudent(@PathParam("index") int index) {
        //Student student = Database.getInstance().getStudent(index);
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
        if(student == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
        return Response.ok(student).build();
    }

    @Path("/{index}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response putStudent(@PathParam("index") int index,@NotNull @Valid Student updStudent) {
        //Student student = Database.getInstance().getStudent(index);
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
        if(student == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }

        if(updStudent.getFirstName() != null) { student.setFirstName(updStudent.getFirstName()); }
        if(updStudent.getLastName() != null) { student.setLastName(updStudent.getLastName()); }
        if(updStudent.getDateOfBirth() != null) { student.setDateOfBirth(updStudent.getDateOfBirth()); }
        datastore.delete(student);
        datastore.save(student);
        return Response.ok(student).build();
    }

    @Path("/{index}")
    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    public Response deleteStudent(@PathParam("index") int index) {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
        if(student == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }

        datastore.delete(student);
        String message = "Student " + index + " deleted";
        return Response.ok(message).build();
    }

    @Path("/{index}/grades")
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response getStudentsGrades(@PathParam("index") int index) {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
        if(student == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }

        List<Grade> studentsGrades = student.getListOfGrades();
        return Response.ok(studentsGrades).build();
    }
}
