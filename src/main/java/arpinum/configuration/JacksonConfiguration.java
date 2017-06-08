package arpinum.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vavr.jackson.datatype.VavrModule;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonConfiguration implements ContextResolver<ObjectMapper> {

    public JacksonConfiguration() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new VavrModule());
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }

    private final ObjectMapper objectMapper;
}