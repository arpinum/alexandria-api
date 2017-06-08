package arpinum.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.cfg4j.provider.ConfigurationProvider;

public class MongoDbModule extends AbstractModule {


    @Provides
    @Singleton
    public MongoClient client(MongoDbConfiguration configuration) {
        return new MongoClient(new MongoClientURI(configuration.uri()));
    }

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public MongoDbConfiguration configuration(ConfigurationProvider provider) {
        return provider.bind("mongodb", MongoDbConfiguration.class);
    }
}
