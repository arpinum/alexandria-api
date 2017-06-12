package catalogue.googlebooks

import com.google.common.util.concurrent.MoreExecutors
import okhttp3.OkHttpClient
import spock.lang.Ignore
import spock.lang.Specification

class CatalogueLivreGoogleTest extends Specification {

    def client = new OkHttpClient.Builder()
            .build()

    static def apiKey = ''

    @Ignore
    def "peut retourner une liste de livres"() {
        given:
        def catalogue = new CatalogueLivreGoogle(MoreExecutors.newDirectExecutorService(), client, apiKey)

        when:
        def résultat = catalogue.recherche("ddd")

        then:
        résultat.get().first() != null
    }
}
