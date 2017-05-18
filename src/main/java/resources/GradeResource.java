package resources;

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
import java.util.List;

/**
 * Created by Darek on 2017-05-04.
 */

@Path("students/{index}/grades")
public class GradeResource {
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<Grade> getStudentsGrades(@PathParam("index") int index) {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
        return student.getListOfGrades();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response postGrade(Grade grade, @PathParam("index") int index, @Context UriInfo uriInfo) {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
        if(student == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
        grade.giveId();
        student.getListOfGrades().add(grade);
 //       int iii = student.getListOfGrades().indexOf(grade); /// jakby co!!!!!!!!!!!!!!!!!!!11
 //       student.getListOfGrades().get(iii).setId(iii);
        datastore.save(grade);
        datastore.save(student);
        String newId = String.valueOf(grade.getId());
        return Response.created(URI.create(uriInfo.getAbsolutePath().toString()+"/"+newId)).entity(grade).build();
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudent(@PathParam("index") int index, @PathParam("id") int id) {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
        if(student == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
        Grade grade = datastore.find(Grade.class).field("id").equal(id).get();
        if(grade == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
        return Response.ok(grade).build();
    }

    @Path("/{index}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response putStudent(@PathParam("index") int index,@NotNull @Valid Student updStudent) {
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

    @Path("/{id}")
    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    public Response deleteStudent(@PathParam("id") int id) {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        //Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
        //if(student == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
        Grade grade = datastore.createQuery(Grade.class).field("id").equal(id).get();
        datastore.delete(grade);
        String message = "Grade " + id + " deleted";
        return Response.ok(message).build();
    }
    @Path("/a")
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<Grade> getgs(@PathParam("index") int index) {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
        return student.getListOfGrades();
    }
    @Path("/b")
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<Grade> getaa() {
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
         //= datastore.createQuery(Student.class).field("index").equal(index).get();
        return datastore.find(Grade.class).asList();
    }

//    @Path("/{index}/grades")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
//    public Response getStudentsGrades(@PathParam("index") int index) {
//        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
//        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
//        if(student == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
//
//        List<Grade> studentsGrades = student.getListOfGrades();
//        return Response.ok(studentsGrades).build();
//    }
}
