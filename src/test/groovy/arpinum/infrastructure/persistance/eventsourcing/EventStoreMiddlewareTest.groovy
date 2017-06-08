package arpinum.infrastructure.persistance.eventsourcing

import arpinum.command.Command
import arpinum.command.CommandBus
import arpinum.ddd.event.Event
import arpinum.ddd.event.EventStore
import com.google.common.util.concurrent.MoreExecutors
import io.vavr.Tuple
import io.vavr.collection.List
import io.vavr.concurrent.Future
import spock.lang.Specification

class EventStoreMiddlewareTest extends Specification {

    def eventStore = Mock(EventStore)

    def middleware = new EventStoreMiddleware(eventStore, MoreExecutors.newDirectExecutorService())

    def "it calls next and saves results"() {
        given:
        def command = {} as Command<String>
        def event = new Event<String>() {

        }
        def events = List.of(event)
        def result = Tuple.of("hello", events)
        def successful = Future.successful(MoreExecutors.newDirectExecutorService(), result)
        when:
        def value = middleware.intercept(Mock(CommandBus), command, { successful })

        then:
        1 * eventStore.save(events)
        value.get() == result
    }

}
