package jodag.generator.datetime;


import java.time.*;
import java.time.temporal.Temporal;


public class DateTimeGenerator extends AbstractDateGenerator<Temporal> {

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

    public Temporal get(Class<?> type) {
        if (type.equals(LocalDate.class)) return getLocalDate();
        if (type.equals(LocalTime.class)) return getLocalTime();
        if (type.equals(LocalDateTime.class)) return getLocalDateTime();
        if (type.equals(OffsetTime.class)) return getOffsetTime();
        if (type.equals(OffsetDateTime.class)) return getOffsetDateTime();
        if (type.equals(Instant.class)) return getInstant();
        throw new UnsupportedOperationException("Unsupported type: " + type);
    }

    public LocalDate getLocalDate() {
        return getRandomDate().toLocalDate();
    }

    public LocalTime getLocalTime() {
        return getRandomDate().toLocalTime();
    }

    public LocalDateTime getLocalDateTime() {
        return getRandomDate();
    }

    public OffsetTime getOffsetTime() {
        return getRandomDate().toLocalTime().atOffset(getRandomOffset());
    }

    public OffsetDateTime getOffsetDateTime() {
        return getRandomDate().atOffset(getRandomOffset());
    }

    public Instant getInstant() {
        return getRandomDate().toInstant(getRandomOffset());
    }
}
