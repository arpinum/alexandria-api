package catalogue.googlebooks

import com.google.common.util.concurrent.MoreExecutors
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import org.junit.Ignore
import spock.lang.Specification

class CatalogueLivreGoogleTest extends Specification {

    def client = new OkHttpClient.Builder()
            .dispatcher(new Dispatcher(MoreExecutors.newDirectExecutorService()))
            .build()

    static def apiKey = ''

    @Ignore
    def "peut retourner une liste de livres"() {
        given:
        def catalogue = new CatalogueLivreGoogle(client, apiKey)

        when:
        def résultat = catalogue.recherche("ddd")

        then:
        résultat.get().first() != null
    }
}
