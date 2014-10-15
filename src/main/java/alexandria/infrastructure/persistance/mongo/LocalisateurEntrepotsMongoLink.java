package alexandria.infrastructure.persistance.mongo;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.EntrepotBibliotheques;
import alexandria.modele.emprunt.EntrepotEmprunts;
import fr.arpinum.graine.infrastructure.persistance.mongo.ContexteMongoLink;

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
    protected EntrepotEmprunts getEmprunts() {
        return new EntrepotEmpruntMongolink(contexteMongoLink.sessionCourante());
    }

    @Override
    protected EntrepotBibliotheques getBibliotheques() {
        return new EntrepotBibliothequesMongolink(contexteMongoLink.sessionCourante());
    }

    private final ContexteMongoLink contexteMongoLink;
}
