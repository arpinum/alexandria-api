package arpinum.infrastructure.bus.event

import arpinum.command.Command
import arpinum.command.CommandBus
import arpinum.ddd.event.Event
import arpinum.ddd.event.EventBus
import com.google.common.util.concurrent.MoreExecutors
import io.vavr.Tuple
import io.vavr.collection.List
import io.vavr.concurrent.Future
import spock.lang.Specification


class EventDispatcherMiddlewareTest extends Specification {

    def eventBus = Mock(EventBus)

    def middleware = new EventDispatcherMiddleware(eventBus)

    def "it dispatches and returns results"() {
        given:
        def command = {} as Command<String>
        def event = new Event<String>() {

        }
        def events = List.of(event)
        def result = Future.successful(MoreExecutors.newDirectExecutorService(), Tuple.of("h", events))

        when:
        def value = middleware.intercept(Mock(CommandBus), command, {result})

        then:
        1 * eventBus.publish(events)
        value == result
    }
}
