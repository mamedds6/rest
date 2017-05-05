package resources;

import model.Student;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Darek on 2017-05-04.
 */

@Path("students")
public class StudentResource {

    @GET
    @Produces("text/plain")
    public String getHello() {
        return "ops";
    }

    @Path("/lol")
    @GET
    @Produces("text/plain")
    public String ops() {

        return "ips";
    }
}
