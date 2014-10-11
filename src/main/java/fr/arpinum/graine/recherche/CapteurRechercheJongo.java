package fr.arpinum.graine.recherche;

import org.jongo.Jongo;

import javax.inject.Inject;

@SuppressWarnings("UnusedDeclaration")
public abstract class CapteurRechercheJongo<TRecherche extends Recherche<TReponse>, TReponse> implements CapteurRecherche<TRecherche, TReponse> {

    public Jongo getJongo() {
        return jongo;
    }

    @Inject
    void setJongo(Jongo jongo) {
        this.jongo = jongo;
    }

    @Override
    public final TReponse execute(TRecherche recherche) {
        return execute(recherche, jongo);
    }

    protected abstract TReponse execute(TRecherche tRecherche, Jongo jongo);

    private Jongo jongo;
}
