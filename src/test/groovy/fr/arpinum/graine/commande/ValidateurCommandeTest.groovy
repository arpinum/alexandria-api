package fr.arpinum.graine.commande

import fr.arpinum.graine.infrastructure.bus.Message
import org.hibernate.validator.constraints.NotEmpty
import spock.lang.Specification

import javax.validation.Validation

class ValidateurCommandeTest extends Specification {

    def factory = Validation.buildDefaultValidatorFactory()
    def validateur = new ValidateurCommande(factory.validator)

    def "peut valider une commande"() {
        when:
        validateur.valide new FauxMessage("")

        then:
        thrown(ValidationException)
    }

    def "peut donner la cause de l'erreur"() {
        when:
        validateur.valide new FauxMessage("")

        then:
        ValidationException exception = thrown()
        !exception.messages().empty
        exception.messages()[0]
    }

    def "appelle la validation en début de synchronisation avec le bus"() {
        when:
        validateur.avantExecution new FauxMessage("")

        then:
        thrown(ValidationException)
    }

    private static class FauxMessage implements Message<String> {
        @NotEmpty
        String nom

        FauxMessage(String nom) {
            this.nom = nom
        }
    }
}
