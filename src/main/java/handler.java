import model.Student;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import resources.Database;
import resources.StudentResource;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Darek on 2017-04-07.
 */
public class handler {
    static Database database = new Database();

    public static void main(String[] args) throws IOException {

        //Database database = new Database();
        database.fillDatalists();

        Student anzy = new Student();
        anzy.setIndex(112225);
        anzy.setFirstName("andżejek");
        anzy.setLastName("dynks");
        Date data = Date.from(Instant.now());
        anzy.setDateOfBirth(data);
        Student lel;// = new Student(123234,"ops","drops", Date.from(Instant.now()));
        anzy = database.students.get(1);
        lel = database.students.get(0);

        System.out.println(anzy.getFirstName());
        System.out.println(anzy.getDateOfBirth());
        System.out.println(lel.getFirstName());
        System.out.println(lel.getDateOfBirth());

        URI baseUri = UriBuilder.fromUri("http://localhost/").port(8080).build();
        ResourceConfig config = new ResourceConfig(StudentResource.class);
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        server.start();

    }
}
