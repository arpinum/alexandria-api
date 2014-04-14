package fr.arpinum.graine.infrastructure.bus

import com.google.common.collect.Sets
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.ListeningExecutorService
import com.google.common.util.concurrent.MoreExecutors
import spock.lang.Specification

import java.util.concurrent.ExecutorService

import static org.mockito.Mockito.*

public class BusAsynchroneTest extends Specification {


    def "peut exécuter une commande"() {
        given:
        def handler = unHandler()
        def bus = busAvec(handler)
        def commande = new FauxMessage()

        when:
        bus.poste(commande)

        then:
        handler.commandeReçue == commande
    }

    def "est asynchrone"() {
        given:
        def executor = Mock(ExecutorService.class)
        def bus = bus()
        bus.setExecutor(executor)

        when:
        bus.poste(new FauxMessage())

        then:
        1 * executor.execute(!null)
    }


    def "encapsule les commandes dans les synchronisations"() {
        given:
        def synchro = Mock(SynchronisationBus)
        def bus = busAvec(synchro)
        def commande = new FauxMessage()

        when:
        bus.poste(commande)

        then:
        1 * synchro.avantExecution(commande)
        then:
        1 * synchro.apresExecution()
    }


    def "sur une erreur appelle tout de même la synchronisation"() {
        given:
        def handler = new FausseCommandeHandler()
        handler.renvoieException()
        def synchronisationBus = Mock(SynchronisationBus)
        def bus = busAvec(handler, synchronisationBus)

        when:
        bus.poste new FauxMessage()

        then:
        1 * synchronisationBus.surErreur()
        then:
        1 * synchronisationBus.finalement()
    }


    def "retourne le résultat d'une commande"() {
        given:
        def handler = new FausseCommandeHandler()
        def bus = busAvec(handler)

        when:
        final ListenableFuture<ResultatExecution<String>> promesse = bus.poste(new FauxMessage())

        then:
        promesse != null
        final ResultatExecution<String> réponse = Futures.getUnchecked(promesse)
        réponse.isSucces()
        réponse.donnees() == "42"
    }

    def "peut retourner directement le résultat"() {
        given:
        def handler = new FausseCommandeHandler()
        def bus = busAvec(handler)

        when:
        def résultat = bus.posteToutDeSuite(new FauxMessage())

        then:
        résultat != null
    }

    def "retourne un résultat sur erreur"() {
        setup:
        def handler = new FausseCommandeHandler()
        handler.renvoieException();
        def bus = busAvec(handler);

        when:
        final ListenableFuture<ResultatExecution<String>> promesse = bus.poste(new FauxMessage())

        then:
        promesse != null
        final ResultatExecution<String> réponse = Futures.getUnchecked(promesse)
        réponse != null
        réponse.isErreur()
        réponse.erreur() instanceof RuntimeException
        réponse.erreur().message == "Ceci est une erreur"
    }

    def "retourne une erreur si aucun handler"() {
        given:
        def bus = unBusVide()

        when:
        def promesse = bus.poste(new FauxMessage())

        then:
        promesse != null
        def resultatExecution = promesse.get()
        resultatExecution.isErreur()
        resultatExecution.erreur() instanceof ErreurBus
    }

    def unBusVide() {
        new BusAsynchrone(Sets.newHashSet(), Sets.newHashSet()) {}
    }

    private BusAsynchrone bus() {
        new BusAsynchrone(Sets.newHashSet(mock(SynchronisationBus.class)), Sets.newHashSet(new FausseCommandeHandler())) {
        };
    }

    private BusAsynchrone busAvec(FausseCommandeHandler handler, SynchronisationBus synchronisationBus) {
        final BusAsynchrone bus = new BusAsynchrone(Sets.newHashSet(synchronisationBus), Sets.newHashSet(handler)) {

        }
        bus.setExecutor(executeur())
        return bus;
    }

    private static ListeningExecutorService executeur() {
        return MoreExecutors.sameThreadExecutor()
    }

    private BusAsynchrone busAvec(SynchronisationBus synchro) {
        return busAvec(unHandler(), synchro)
    }

    private BusAsynchrone busAvec(FausseCommandeHandler handler) {
        busAvec(handler, mock(SynchronisationBus.class))
    }

    private FausseCommandeHandler unHandler() {
        new FausseCommandeHandler()
    }

    private class FauxMessage implements Message<String> {

        private FauxMessage() {
        }


    }

    private class FausseCommandeHandler implements HandlerMessage<FauxMessage, String> {

        @Override
        public String execute(FauxMessage commande) {
            commandeReçue = commande;
            if (exception) {
                throw new RuntimeException("Ceci est une erreur");
            }
            return "42";
        }

        public void renvoieException() {
            this.exception = true;
        }

        private FauxMessage commandeReçue;
        private boolean exception;
    }
}
