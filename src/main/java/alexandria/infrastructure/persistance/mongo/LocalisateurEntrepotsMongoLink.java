package alexandria.infrastructure.persistance.mongo;

import alexandria.modele.bibliotheque.EntrepotBibliotheques;
import alexandria.modele.emprunt.EntrepotEmprunt;
import fr.arpinum.graine.infrastructure.persistance.mongo.ContexteMongoLink;
import alexandria.modele.LocalisateurEntrepots;

import javax.inject.Inject;


public class LocalisateurEntrepotsMongoLink extends LocalisateurEntrepots {

    @Inject
    public LocalisateurEntrepotsMongoLink(ContexteMongoLink contexteMongoLink) {
        this.contexteMongoLink = contexteMongoLink;
    }

    protected ContexteMongoLink contexte() {
        return contexteMongoLink;
    }

    @Override
    protected EntrepotBibliotheques getBibliotheques() {
        return new EntrepotBibliothequesMongolink(contexteMongoLink.sessionCourante());
    }

    @Override
    protected EntrepotEmprunt getEmprunt() {
        return new EntrepotEmpruntsMongoLink(contexteMongoLink.sessionCourante());
    }

    private final ContexteMongoLink contexteMongoLink;
}
