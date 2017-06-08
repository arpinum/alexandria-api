package arpinum.query;

import arpinum.infrastructure.persistance.JongoProvider;
import arpinum.query.Query;
import arpinum.query.QueryHandler;
import org.jongo.*;

import javax.inject.Inject;

@SuppressWarnings("UnusedDeclaration")
public abstract class QueryHandlerJongo<TRecherche extends Query<TReponse>, TReponse> implements QueryHandler<TRecherche, TReponse> {

    @Inject
    protected void setJongo(JongoProvider provider) {
        this.jongo = provider;
    }

    @Override
    public final TReponse execute(TRecherche command) {
        return execute(command, jongo.current());
    }

    protected abstract TReponse execute(TRecherche tRecherche, Jongo jongo);

    protected Find applyLimitAndSkip(Query<?> query, Find find) {
        return find.skip(query.skip()).limit(query.limit());
    }

    private JongoProvider jongo;
}
