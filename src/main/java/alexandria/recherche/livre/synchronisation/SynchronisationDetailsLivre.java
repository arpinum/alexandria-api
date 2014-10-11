package alexandria.recherche.livre.synchronisation;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.Bibliotheque;
import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement;
import alexandria.recherche.livre.Livre;
import alexandria.recherche.livre.ResumeExemplaire;
import catalogue.CatalogueLivre;
import catalogue.DetailsLivre;
import fr.arpinum.graine.modele.evenement.CapteurEvenement;
import org.jongo.Jongo;

import javax.inject.Inject;

public class SynchronisationDetailsLivre implements CapteurEvenement<ExemplaireAjouteEvenement>{

    @Inject
    public SynchronisationDetailsLivre(Jongo jongo, CatalogueLivre catalogue) {
        this.jongo = jongo;
        this.catalogue = catalogue;
    }

    @Override
    public void executeEvenement(ExemplaireAjouteEvenement evenement) {
        final Bibliotheque bibliotheque = LocalisateurEntrepots.bibliotheques().get(evenement.idBibliotheque);
        final Livre livre = new Livre(evenement.isbn, catalogue.parIsbn(evenement.isbn).orElse(DetailsLivre.LIVRE_VIDE));
        livre.exemplaires.add(new ResumeExemplaire(bibliotheque.emailLecteur()));
        jongo.getCollection("vue_detailslivre").update("{_id:#}", livre.isbn).upsert().with(livre);

    }

    private final Jongo jongo;
    private final CatalogueLivre catalogue;
}
