import model.Grade;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import utilities.DatastoreHandler;
import model.Student;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.mongodb.morphia.Datastore;
import resources.StudentResource;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.Date;

/**
 * Created by Darek on 2017-04-07.
 */
public class handler {
    public static void main(String[] args) throws IOException {
        DatastoreHandler datastoreHandler = DatastoreHandler.getInstance();
        Datastore datastore = datastoreHandler.getDatastore();

        datastoreHandler.fillDatastore();

        System.out.println("MongoDB OK");
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(8080).build();
        ResourceConfig config = new ResourceConfig().packages("resources").register(DeclarativeLinkingFeature.class);
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        server.start();
        //System.out.println(String.valueOf(datastore.find(Grade.class).order("-id").get().getGradeId()));
        //System.out.println(String.valueOf(datastore.find(Student.class).order("-grades").order("-id").get().getListOfGrades().get));

    }
}
