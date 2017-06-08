package arpinum.infrastructure.bus.command

import arpinum.command.Command
import arpinum.command.CommandBus
import arpinum.command.CommandHandler
import arpinum.command.CommandMiddleware
import arpinum.ddd.event.Event
import com.google.common.util.concurrent.MoreExecutors
import io.vavr.Tuple
import io.vavr.Tuple2
import io.vavr.collection.List
import io.vavr.collection.Seq
import io.vavr.concurrent.Future
import spock.lang.Specification

import java.util.function.Supplier

class CommandBusAsynchronousTest extends Specification {

    def executor = MoreExecutors.newDirectExecutorService()


    def "invokes handler"() {
        given:
        def handler = new FakeCommandHandler()
        def bus = new CommandBusAsynchronous([] as Set, [handler] as Set, executor)
        def command = new FakeMessage()

        when:
        def result = bus.send(command)

        then:
        handler.message == command
        result.get() == 'response'

    }

    def "invokes middleware and pass through"() {
        given:
        def handler = new FakeCommandHandler()
        def middleware = new FakeMiddleware()


        def bus = new CommandBusAsynchronous([middleware] as Set, [handler] as Set, executor)
        def command = new FakeMessage()

        when:
        def response = bus.send(command)

        then:
        handler.message == command
        middleware.called
        response.get() == 'response'
    }

    private class FakeCommandHandler implements CommandHandler<FakeMessage, String> {

        def message

        @Override
        Tuple2<String, Seq<Event<?>>> execute(FakeMessage fakeMessage) {
            message = fakeMessage
            Tuple.of("response", List.empty())
        }
    }

    private class FakeMessage implements Command<String> {

        private FakeMessage() {
        }
    }

    private class FakeMiddleware implements CommandMiddleware {

        boolean called
        def calls = []

        FakeMiddleware() {
        }

        FakeMiddleware(calls) {
            this.calls = calls
        }

        @Override
        <T> Future<Tuple2<T, Seq<Event>>> intercept(CommandBus bus, Command<T> message, Supplier<Future<Tuple2<T, Seq<Event>>>> next) {
            called = true
            calls << this
            next.get()
        }
    }

    def "chain middlewares"() {
        given:
        def handler = new FakeCommandHandler()
        def calls = []
        def firstMiddleware = new FakeMiddleware(calls)
        def secondMiddleware = new FakeMiddleware(calls)
        def bus = new CommandBusAsynchronous([firstMiddleware, secondMiddleware] as Set, [handler] as Set, executor)
        def command = new FakeMessage()

        when:
        def response = bus.send(command)

        then:
        firstMiddleware.called
        secondMiddleware.called
        calls == [firstMiddleware, secondMiddleware]
        response.get() == 'response'
    }

}
