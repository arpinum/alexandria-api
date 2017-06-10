package arpinum.infrastructure

import arpinum.ddd.event.Event
import io.vavr.collection.List
import io.vavr.collection.Stream
import org.junit.Rule

import arpinum.infrastructure.persistance.eventsourcing.EventStoreJongo
import arpinum.query.WithJongo
import spock.lang.Specification

import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

class EventStoreJongoTest extends Specification {

    @Rule
    WithJongo jongo = new WithJongo()

    EventStoreJongo store

    void setup() {
        store = new EventStoreJongo("fake", jongo.jongo())
    }

    def "can persist"() {
        given:
        def entity = new FakeEntity(id: "getId")
        def event = new FakeEvent(entity, "stuff")

        when:
        store.save(List.of(event))

        then:
        def record = jongo.collection("fake_eventstore").findOne()
        record != null
        record.targetId == "getId"
        record.targetType == "FakeEntity"
        record.timestamp != null
        record.someData == "stuff"
        record._class == 'arpinum.infrastructure.EventStoreJongoTest$FakeEvent'
    }

    def "can load"() {
        given:
        jongo.collection('fake_eventstore') << [targetId    : "getId"
                                                , targetType: "FakeEntity"
                                                , timestamp : Instant.EPOCH.toEpochMilli()
                                                , someData  : "data"
                                                , _class    : 'arpinum.infrastructure.EventStoreJongoTest$FakeEvent']

        when:
        def events = store.allOf(FakeEntity.class).consume({s->s.toJavaList()})

        then:
        events != null
        events.size == 1
        def event = events[0] as FakeEvent
        event.someData == "data"
        event.timestamp == 0
        event.targetId == "getId"
        event.targetType == "FakeEntity"
    }

    def "can load given event type"() {
        given:
        jongo.collection('fake_eventstore') << [targetId    : "getId"
                                                , targetType: "FakeEntity"
                                                , timestamp : Instant.EPOCH.toEpochMilli()
                                                , someData  : "data"
                                                , _class    : 'arpinum.infrastructure.EventStoreJongoTest$FakeEvent']

        when:
        def events = store.allOfWithType(FakeEntity, FakeEvent).consume({s->s.toJavaList()})

        then:
        events != null
        events.size() == 1
    }

    def "mark as deleted"() {
        given:
        jongo.collection('fake_eventstore') << [targetId    : "getId"
                                                , targetType: "FakeEntity"
                                                , timestamp : Instant.EPOCH.toEpochMilli()
                                                , someData  : "data"
                                                , _class    : 'arpinum.infrastructure.EventStoreJongoTest$FakeEvent']

        when:
        store.markAllAsDeleted("getId", FakeEntity.class)

        then:
        def record = jongo.collection('fake_eventstore').findOne([targetId: "getId"])
        record._deleted == true
    }

    def "do not load delete events"() {
        given:
        jongo.collection('fake_eventstore') << [targetId    : "getId"
                                                , targetType: "FakeEntity"
                                                , timestamp : Instant.EPOCH.toEpochMilli()
                                                , someData  : "data"
                                                , _deleted  : true
                                                , _class    : 'arpinum.infrastructure.EventStoreJongoTest$FakeEvent']

        when:
        def events = store.allOf(FakeEntity.class).consume({s->s.toJavaList()})

        then:
        events != null
        events.size == 0
    }

    def "loads ordered by timestamp"() {
        given:
        def first = new FakeEvent(new FakeEntity(id: "getId"), "dataFirst")
        store.save(List.of(first))
        def second = new FakeEvent(new FakeEntity(id: "getId"), "dataSecond")
        store.save(List.of(second))

        when:
        def events = store.allOf("getId", FakeEntity.class).consume({it.toJavaList()})

        then:
        events[0].someData == "dataFirst"
        events[1].someData == "dataSecond"
    }

    def "deals with duration"() {
        given:
        def entity = new FakeEntity()
        store.save(List.of(new FakeEventWithDuration(entity, Duration.of(30, ChronoUnit.DAYS))))

        when:
        def result = store.allOf(entity.id, FakeEntity).consume({it.toJavaList()})

        then:
        result.size() == 1
    }

    private static class FakeEvent extends Event<FakeEntity> {

        FakeEvent() {

        }

        FakeEvent(FakeEntity entity, String data) {
            super(entity.id)
            someData = data
        }

        String someData
    }

    private static class FakeEventWithDuration extends Event<FakeEntity> {

        FakeEventWithDuration() {
        }

        FakeEventWithDuration(FakeEntity entity, Duration duration) {
            super(entity.id)
            this.duration = duration
        }

        Duration duration
        String someData
    }

    private static class FakeEntity {
        public String id
    }
}
