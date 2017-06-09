package resources;

import model.Course;
import org.mongodb.morphia.query.Query;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Darek on 2017-05-04.
 */

@Path("students")
public class StudentResource {
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Student> getStudents(@QueryParam("firstName") String firstName,
                                     @QueryParam("lastName") String lastName,
                                     @QueryParam("index") int index,
                                     @QueryParam("born") Date born,
                                     @QueryParam("bornBefore") Date bornBefore,
                                     @QueryParam("bornAfter") Date bornAfter){
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        List<Student> students = datastore.find(Student.class).order("index").asList();

        Query query = datastore.createQuery(Student.class);

        if (index!=0)
        students = students.stream().filter(student -> student.getIndex() == index).collect(Collectors.toList());

        if(firstName!=null)
            query.field("firstName").containsIgnoreCase(firstName);

        if(lastName!=null)
        students = students.stream().filter(student -> student.getLastName().equals(lastName)).collect(Collectors.toList());

        if(born!=null)
            //System.out.println(born+" "+ students.get(2).getDateOfBirth());
            query.field("dateOfBirth").equal(born);

        if(bornAfter!=null)
            query.field("dateOfBirth").greaterThan(bornAfter);

        if(bornBefore!=null)
            query.field("dateOfBirth").lessThan(bornBefore);

        students = query.asList();
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
        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
        if(student == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
        //if(updStudent.getFirstName() != null) { student.setFirstName(updStudent.getFirstName()); }
        //if(updStudent.getLastName() != null) { student.setLastName(updStudent.getLastName()); }
        //if(updStudent.getDateOfBirth() != null) { student.setDateOfBirth(updStudent.getDateOfBirth()); }
        student.setFirstName(updStudent.getFirstName());
        student.setLastName(updStudent.getLastName());
        student.setDateOfBirth(updStudent.getDateOfBirth());

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

    //jest w gradeResource
//    @Path("/{index}/grades")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
//    public List<Grade> getStudentsGrades(@PathParam("index") int index) {
//        Datastore datastore = DatastoreHandler.getInstance().getDatastore();
//        Student student = datastore.createQuery(Student.class).field("index").equal(index).get();
//        //if(student == null) { return Response.noContent().status(Response.Status.NOT_FOUND).build(); }
//        //List<Grade> studentsGrades = student.getListOfGrades();
//        return student.getListOfGrades();
//    }
}

//@QueryParam("firstName") String firstName,
//@QueryParam("lastName") String lastName,
//    @DefaultValue("0") @QueryParam("direction") int direction,
//    @QueryParam("indexQuery") int index,
//    @QueryParam("dateOfBirthQuery") Date date,
//    @QueryParam("firstNameQuery") String firstNameQuery,
//    @QueryParam("lastNameQuery") String lastNameQuery
