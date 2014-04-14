package fr.arpinum.graine.recherche

import com.github.fakemongo.Fongo
import com.gmongo.GMongo
import com.mongodb.DB
import com.mongodb.DBCollection
import org.jongo.Jongo
import org.junit.rules.ExternalResource

@SuppressWarnings("GroovyUnusedDeclaration")
class AvecJongo extends ExternalResource {
    private Jongo jongo
    private Fongo fongo

    @Override
    protected void before() throws Throwable {
        fongo = new Fongo("Test")
        jongo = new Jongo(this.fongo.getDB("test"))
    }

    public jongo() {
        return jongo;
    }

    public DB db() {
        return new GMongo(fongo.mongo).getDB("test");
    }

    public DBCollection collection(String collection) {
        return db().getCollection(collection);
    }

    @Override
    protected void after() {
        fongo.dropDatabase("test");
    }
}
