package arpinum.query

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.github.fakemongo.Fongo
import com.gmongo.GMongo
import com.mongodb.DB
import com.mongodb.DBCollection
import org.jongo.Jongo
import org.jongo.marshall.jackson.JacksonMapper
import org.junit.rules.ExternalResource

@SuppressWarnings("GroovyUnusedDeclaration")
class WithJongo extends ExternalResource {
    private Jongo jongo
    private Fongo fongo

    @Override
    protected void before() throws Throwable {
        fongo = new Fongo("Test")
        jongo = new Jongo(this.fongo.getDB("test"), new JacksonMapper.Builder()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
                .build())
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
