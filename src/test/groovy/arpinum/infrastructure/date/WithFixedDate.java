package arpinum.infrastructure.date;

import org.junit.rules.ExternalResource;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class WithFixedDate extends ExternalResource {

    public static WithFixedDate of(LocalDateTime date) {
        return new WithFixedDate(date);
    }

    public WithFixedDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    protected void before() throws Throwable {
        Instant instant = date.toInstant(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
        Dates.initialize(new Dates(Clock.fixed(instant, ZoneOffset.UTC)));
    }

    @Override
    protected void after() {
        Dates.initialize(new Dates(Clock.systemUTC()));
    }

    public void twoHoursLater() {
        later(2, ChronoUnit.HOURS);
    }

    public void later(int amount, ChronoUnit unit) {
        final LocalDateTime newDate = Dates.localNow().plus(amount, unit);
        Dates.initialize(new Dates(Clock.fixed(newDate.toInstant(ZoneOffset.UTC), ZoneOffset.UTC)));
    }

    public void setNow(LocalDateTime time) {
        Dates.initialize(new Dates(Clock.fixed(time.toInstant(ZoneOffset.UTC), ZoneOffset.UTC)));
    }

    private final LocalDateTime date;
}
