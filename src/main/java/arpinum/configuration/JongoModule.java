package arpinum.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.cfg4j.provider.ConfigurationProvider;
import org.jongo.Jongo;
import arpinum.infrastructure.persistance.JongoBuilder;
import arpinum.infrastructure.persistance.JongoProvider;

import java.net.UnknownHostException;

public class JongoModule extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public Jongo jongo(MongoDbConfiguration mongoDbConfiguration, MongoClient client) throws UnknownHostException {
        MongoClientURI mongoClientURI = new MongoClientURI(mongoDbConfiguration.uri());
        return JongoBuilder.build(client.getDB(mongoClientURI.getDatabase()));
    }

    @Provides
    @Singleton
    public JongoProvider jongoProvider(Jongo jongo) {
        return () -> jongo;
    }

}
