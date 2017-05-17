import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by Darek on 2017-05-17.
 */
public class DatastoreHandler {
    private static DatastoreHandler instance = new DatastoreHandler();
    private Datastore datastore;

    public static DatastoreHandler getInstance() {
        return instance;
    }
    private DatastoreHandler() {
        final Morphia morphia = new Morphia();
            // tell Morphia where to find your classes
            // can be called multiple times with different packages or classes
        morphia.mapPackage("model");
            // create the Datastore connecting to the default port on the local host
        datastore = morphia.createDatastore(new MongoClient("localhost", 8004), "rest");
        datastore.ensureIndexes();
    }
    public Datastore getDatastore() {
        return datastore;
    }

}
