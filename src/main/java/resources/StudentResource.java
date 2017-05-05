package resources;

import model.Student;

import javax.ws.rs.*;
import java.time.Instant;
import java.util.Date;

/**
 * Created by Darek on 2017-05-04.
 */

@Path("students")
public class StudentResource {

    @GET
    @Produces("text/plain")
    public String getHello() {
        String kappa;
        kappa = ops(0) + ops(1);

        return kappa;
    }

    @Path("/{index}")
    @GET
    @Produces("text/plain")
    public String ops(@PathParam("index") int index) {
        Student student = Database.getInstance().getStudent(index);
        String firstName = student.getFirstName();

        return firstName;
    }

    @Path("/{index}")
    @PUT
    @Produces("text/plain")
    public String putStudent(@PathParam("index") int index) {
        Student student = new Student(index, "Andrzej", "Duda", Date.from(Instant.now()));
        Database.getInstance().addStudent(student);
        String firstName = student.getFirstName();

        return firstName;
    }


/////////////////////
    @Path("/qwe")
    @GET
    @Produces("text/plain")
    public String dops() {

        return "dops";
    }
}
