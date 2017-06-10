package authentification.application

import authentification.infrastructure.dao.mémoire.UtilisateurDaoMémoire
import authentification.modele.Utilisateur
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator
import org.pac4j.jwt.profile.JwtGenerator
import org.pac4j.jwt.profile.JwtProfile
import spock.lang.Specification

class UtilisateurServiceTest extends Specification {

    def key = '5RPD9z_uF4Nrg1qAlJU6D1TdpF-Z6N18iHJdiOICTsAROz40CasyCbXP2x5EIKAlFzDPh4NDHEPZxewdNSouTeuWstK_igwQWiH2ONPonpoR8RDcP3pQFJKHrx4lFIZ56BybC_V-35ConGy-SEa3Ck-o-7hgsnF3FDIvVEEv57EKQrKbdZi4OEsCpP2lN22zoV4NPylJ0b1Fne9ti4Vzj1jHL0LPGEPfZQGwpXevriyAzBUMOUwKhDr2yGVm4Oz2u5laMvTXtyZlhv14lKEiS7JWzrPxOSrxtoxQlnR807h7QIdPkcEbg_9J3yBu_vxUrSpdsGG6KsIrkATlVerx1w'

    def dao = new UtilisateurDaoMémoire()

    def configuration = new SecretSignatureConfiguration(key)
    def generator = new JwtGenerator<JwtProfile>(configuration)
    def service = new UtilisateurService(dao, generator)

    def "fabrique un token valide"() {
        given:
        def utilisateur = Utilisateur.crée("email@email", "jb", "dusse")
        dao.ajoute(utilisateur)

        when:
        def token = service.authentifie('email@email', 'jb', 'dusse')

        then:
        token != null
        def profile = new JwtAuthenticator(configuration).validateToken(token)
        profile.id == utilisateur.id
        profile.firstName == 'jb'
        profile.familyName == 'dusse'
    }
}
