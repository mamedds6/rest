package resources;

import model.Grade;
import model.Student;
import org.glassfish.grizzly.http.server.util.StringParser;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Created by Darek on 2017-05-04.
 */

@Path("students")
public class StudentResource {
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Student> getStudents() {
        List<Student> students = Database.getInstance().getStudents();
        return students;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response postStudent(@NotNull @Valid Student student, @Context UriInfo uriInfo) {
        Database.getInstance().addStudent(student);
        String newIndex = String.valueOf(student.getIndex());
        //na pewno źle, trzeba ten jednoznaczny indeks dodac
        return Response.created(URI.create(uriInfo.getAbsolutePath().toString()+newIndex)).entity(student).build();
    }

    @Path("/{index}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudent(@PathParam("index") int index) {
        Student student = Database.getInstance().getStudent(index);
        if(student == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
        return Response.ok(student).build();
    }

    @Path("/{index}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response putStudent(@PathParam("index") int index,@NotNull @Valid Student updStudent) {
        Student student = Database.getInstance().getStudent(index);
        if(student == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
        if(updStudent.getFirstName() != null) { student.setFirstName(updStudent.getFirstName()); }
        if(updStudent.getLastName() != null) { student.setLastName(updStudent.getLastName()); }
        if(updStudent.getIndex() != 0) { student.setIndex(updStudent.getIndex()); }
        if(updStudent.getDateOfBirth() != null) { student.setDateOfBirth(updStudent.getDateOfBirth()); }
        return Response.ok(student).build();
    }

    @Path("/{index}")
    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    public Response deleteStudent(@PathParam("index") int index) {
        if(!Database.getInstance().deleteStudent(index))
            return Response.noContent().status(Response.Status.NOT_FOUND).build();
        String message = "Student " + index + " deleted";
        return Response.ok(message).build();
    }

    @Path("/{index}/grades")
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response getStudentsGrades(@PathParam("index") int index) {
        Student student = Database.getInstance().getStudent(index);
        if(student == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
        List<Grade> studentsGrades = student.getListOfGrades();
        return Response.ok(studentsGrades).build();
    }
}
