package arpinum.query;


import com.google.common.reflect.TypeToken;

public interface QueryHandler<TQuery extends Query<TResponse>, TResponse> {

    TResponse execute(TQuery command);

    default Class<TQuery> queryType() {
        return (Class<TQuery>) new TypeToken<TQuery>(getClass()) {
        }.getRawType();
    }
}
