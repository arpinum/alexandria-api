package fr.arpinum.graine.infrastructure.persistance.mongo

import com.gmongo.GMongo
import com.mongodb.DB
import com.mongodb.DBCollection
import org.junit.rules.ExternalResource
import org.mongolink.MongoSession
import org.mongolink.test.MongolinkRule

public class AvecMongolink extends ExternalResource {

    public static AvecMongolink avecPackage(String nomPackage) {
        final AvecMongolink résultat = new AvecMongolink()
        résultat.mongolink = MongolinkRule.withPackage(nomPackage)
        return résultat
    }

    private AvecMongolink() {
    }

    @Override
    protected void before() throws Throwable {
        mongolink.before()
        gMongo = new GMongo(mongolink.getCurrentSession().getDb().getMongo())
    }

    @Override
    protected void after() {
        mongolink.after()
    }


    public void nettoieSession() {
        mongolink.cleanSession()
    }

    public MongoSession sessionCourante() {
        return mongolink.getCurrentSession()
    }

    public DB db() {
        return gMongo.getDB("test")
    }

    public DBCollection collection(String collection) {
        return db().getCollection(collection)
    }

    private MongolinkRule mongolink
    private GMongo gMongo
}
