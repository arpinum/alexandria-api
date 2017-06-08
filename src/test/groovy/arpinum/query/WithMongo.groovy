package arpinum.query
import com.gmongo.GMongoClient
import com.mongodb.DB
import com.mongodb.DBCollection
import org.jongo.Jongo
import org.junit.rules.ExternalResource

@SuppressWarnings("GroovyUnusedDeclaration")
class WithMongo extends ExternalResource {
    private Jongo jongo
    private DB db



    @Override
    protected void before() throws Throwable {
        db = new GMongoClient().getDB()
        jongo = new Jongo(db)
    }

    public jongo() {
        return jongo;
    }

    public DB db() {
        return db;
    }

    public DBCollection collection(String collection) {
        return db().getCollection(collection);
    }

    @Override
    protected void after() {
        db.dropDatabase()
    }
}
