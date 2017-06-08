package arpinum.infrastructure.persistance;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.DB;
import io.vavr.jackson.datatype.VavrModule;
import org.jongo.Jongo;
import org.jongo.marshall.jackson.JacksonMapper;

public final class JongoBuilder {
    private JongoBuilder() {
    }

    public static Jongo build(DB db) {
        return new Jongo(db, new JacksonMapper.Builder()
                .registerModule(new JavaTimeModule())
                .registerModule(new VavrModule())
                .disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
                .build());
    }
}
