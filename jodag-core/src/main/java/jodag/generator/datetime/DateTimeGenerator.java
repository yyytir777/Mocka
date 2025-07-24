package jodag.generator.datetime;

import jodag.generator.AbstractGenerator;

import java.time.*;
import java.time.temporal.Temporal;


public class DateTimeGenerator extends AbstractGenerator<Temporal> {

    private static final DateTimeGenerator INSTANCE = new DateTimeGenerator();

    private DateTimeGenerator() {
        super("datetime", Temporal.class);
    }

    public static DateTimeGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Temporal get() {
        return LocalDateTime.now();
    }

    public LocalDate getLocalDate() {
        return LocalDate.now();
    }

    public LocalTime getLocalTime() {
        return LocalTime.now();
    }

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    public OffsetTime getOffsetTime() {
        return OffsetTime.now();
    }

    public OffsetDateTime getOffsetDateTime() {
        return OffsetDateTime.now();
    }

    public Instant getInstant() {
        return Instant.now();
    }
}
