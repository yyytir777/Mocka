package entityinstantiator.generator.datetime;


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

    /**
     * Returns a random value of java.time.LocalDateTime.now()
     */
    @Override
    public Temporal get() {
        return LocalDateTime.now();
    }

    /**
     * Returns a random value of the java.time type
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        if (type.equals(LocalDate.class)) return (T) getLocalDate();
        if (type.equals(LocalTime.class)) return (T) getLocalTime();
        if (type.equals(LocalDateTime.class)) return (T) getLocalDateTime();
        if (type.equals(OffsetTime.class)) return (T) getOffsetTime();
        if (type.equals(OffsetDateTime.class)) return (T) getOffsetDateTime();
        if (type.equals(Instant.class)) return (T) getInstant();
        throw new UnsupportedOperationException("Unsupported type: " + type);
    }

    /**
     * Returns a random value of java.time.LocalDate
     */
    public LocalDate getLocalDate() {
        return getRandomDate().toLocalDate();
    }

    /**
     * Returns a random value of java.time.LocalTime
     */
    public LocalTime getLocalTime() {
        return getRandomDate().toLocalTime();
    }

    /**
     * Returns a random value of java.time.LocalDateTime
     */
    public LocalDateTime getLocalDateTime() {
        return getRandomDate();
    }

    /**
     * Returns a random value of java.time.OffsetTime
     */
    public OffsetTime getOffsetTime() {
        return getRandomDate().toLocalTime().atOffset(getRandomOffset());
    }

    /**
     * Returns a random value of java.time.OffsetDateTime
     */
    public OffsetDateTime getOffsetDateTime() {
        return getRandomDate().atOffset(getRandomOffset());
    }

    /**
     * Returns a random value of java.time.Instant
     */
    public Instant getInstant() {
        return getRandomDate().toInstant(getRandomOffset());
    }
}
