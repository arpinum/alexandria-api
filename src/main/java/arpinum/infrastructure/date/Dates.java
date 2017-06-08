package arpinum.infrastructure.date;

import java.time.*;
import java.util.Date;

public class Dates {

    Dates(Clock clock) {
        this.clock = clock;
    }

    public static void initialize(Dates instance) {
        INSTANCE = instance;
    }

    public static LocalDateTime localNow() {
        return LocalDateTime.now(INSTANCE.clock);
    }

    public static Date now() {
        return INSTANCE.getNow();
    }

    protected Date getNow() {
        return Date.from(clock.instant());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, INSTANCE.clock.getZone());
    }

    public static Instant instantNow() {
        return INSTANCE.clock.instant();
    }

    public static Date toDate(LocalDate date) {
        return toDate(date.atStartOfDay());
    }

    public static Date toDate(LocalDateTime date) {

        return Date.from(date.atZone(INSTANCE.clock.getZone()).toInstant());
    }

    public static Date fromTimestamp(long timestamp) {
        return Date.from(Instant.ofEpochMilli(timestamp));
    }

    private Clock clock;


    private static Dates INSTANCE = new Dates(Clock.systemUTC());
}
