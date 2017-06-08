package arpinum.infrastructure.persistance.query;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.jongo.*;

import java.util.*;
import java.util.stream.Collectors;

public class QueryBuilder {

    public static QueryBuilder create(String collection) {
        return new QueryBuilder(collection);
    }

    private QueryBuilder(String collection) {
        this.collection = collection;
    }

    public QueryBuilder add(String field, String query, Object... values) {
        parts.add(new QueryPart(field, query, values));
        return this;
    }

    public Find find(Jongo jongo) {
        return jongo.getCollection(collection).find(query(), parameters());
    }

    public FindOne findOne(Jongo jongo) {
        return jongo.getCollection(collection).findOne(query(), parameters());
    }

    private String query() {
        if(parts.isEmpty()) {
            return "{}";
        }
        final StringBuilder builder = new StringBuilder();
        builder.append("{$and:[");
        Joiner.on(",").appendTo(builder, parts.stream().map(p -> p.buildQuery()).collect(Collectors.toList()));
        builder.append("]}");
        return builder.toString();
    }

    private Object[] parameters() {
        final ArrayList<Object> objects = Lists.newArrayList();
        parts.forEach(p -> p.applyParameters(objects));
        return objects.toArray();
    }

    private final String collection;
    private List<QueryPart> parts = Lists.newArrayList();

    private class QueryPart {

        public QueryPart(String field, String query, Object[] values) {
            this.field = field;
            this.query = query;
            this.values = values;
        }

        public void applyParameters(List<Object> objects) {
            for (Object value : values) {
                objects.add(value);
            }
        }

        public String buildQuery() {
            return "{" + field + ":" + query + "}";
        }

        private final String field;
        private final String query;
        private final Object[] values;
    }
}
