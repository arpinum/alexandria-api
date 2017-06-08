package arpinum.ddd.event

import arpinum.ddd.BaseAggregate
import arpinum.infrastructure.bus.event.EventBusAsynchronous
import com.google.common.util.concurrent.MoreExecutors
import io.vavr.collection.List
import spock.lang.Specification

class EventBusAsynchronousTest extends Specification {

    def executor = MoreExecutors.newDirectExecutorService()

    def "invokes handler"() {
        given:
        def captor = new FakeEventCaptor()
        def bus = new EventBusAsynchronous([] as Set, [captor] as Set, executor)
        def event = new FakeEvent()

        when:
        bus.publish(List.of(event))

        then:
        captor.called
        captor.event == event

    }

    def "invokes middleware"() {
        given:
        def middleware = new FakeBusMiddleware()
        def bus = new EventBusAsynchronous([middleware] as Set, [new FakeEventCaptor()] as Set, executor)
        def event = new FakeEvent()

        when:
        bus.publish(List.of(event))

        then:
        middleware.event == event
    }

    static class FakeEvent extends Event<FakeAggregate> {

    }

    static class FakeAggregate extends BaseAggregate<String> {

    }

    static class FakeEventCaptor implements EventCaptor<FakeEvent> {

        boolean called
        FakeEvent event

        @Override
        void execute(FakeEvent event) {
            this.event = event
            called = true
        }
    }

    static class FakeBusMiddleware implements EventBusMiddleware {

        Event<?> event

        @Override
        void intercept(Event<?> event, Runnable next) {
            this.event = event
            next.run()
        }
    }

}
