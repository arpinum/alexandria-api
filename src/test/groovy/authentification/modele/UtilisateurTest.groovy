package authentification.modele

import spock.lang.Specification


class UtilisateurTest extends Specification {

    def "calcule un id stable"(def email1, def email2, def equals) {
        given:
        def premier = new Utilisateur(email1)
        def second = new Utilisateur(email2)

        when:
        def idEquals = premier.id == second.id

        then:
        idEquals == equals

        where:
        email1 | email2 | equals
        "same" | "same" | true
    }
}
