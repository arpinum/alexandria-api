package arpinum.query;

import java.util.function.Supplier;

public interface QueryMiddleware {

    <T> T intercept(Query<T> query, Supplier<T> next);
}
