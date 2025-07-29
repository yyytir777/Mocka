package jodag.generator.datetime;

import java.time.*;

public class CommonDateGenerator {

    private static final CommonDateGenerator INSTANCE = new CommonDateGenerator();

    private final DateTimeGenerator dateTimeGenerator = DateTimeGenerator.getInstance();
    private final LegacyDateGenerator legacyDateGenerator = LegacyDateGenerator.getInstance();
    private final SqlDateGenerator sqlDateGenerator = SqlDateGenerator.getInstance();

    private CommonDateGenerator() {}

    public static CommonDateGenerator getInstance() {
        return INSTANCE;
    }

    // java.time
    public LocalDate getLocalDate() {
        return dateTimeGenerator.getLocalDate();
    }

    public LocalTime getLocalTime() {
        return dateTimeGenerator.getLocalTime();
    }

    public LocalDateTime getLocalDateTime() {
        return dateTimeGenerator.getLocalDateTime();
    }

    public OffsetTime getOffsetTime() {
        return dateTimeGenerator.getOffsetTime();
    }

    public OffsetDateTime getOffsetDateTime() {
        return dateTimeGenerator.getOffsetDateTime();
    }

    public Instant getInstant() {
        return dateTimeGenerator.getInstant();
    }

    // java.util
    public java.util.Date getUtilDate() {
        return legacyDateGenerator.getDate();
    }

    public java.util.Calendar getCalendar() {
        return legacyDateGenerator.getCalendar();
    }

    // java.sql
    public java.sql.Date getSqlDate() {
        return sqlDateGenerator.getDate();
    }

    public java.sql.Time getSqlTime() {
        return sqlDateGenerator.getTime();
    }

    public java.sql.Timestamp getSqlTimestamp() {
        return sqlDateGenerator.getTimestamp();
    }

    public Object get(Class<?> type) {
        // java.time
        if (type.equals(LocalDate.class)) return getLocalDate();
        if (type.equals(LocalTime.class)) return getLocalTime();
        if (type.equals(LocalDateTime.class)) return getLocalDateTime();
        if (type.equals(OffsetTime.class)) return getOffsetTime();
        if (type.equals(OffsetDateTime.class)) return getOffsetDateTime();
        if (type.equals(Instant.class)) return getInstant();

        // java.util
        if (type.equals(java.util.Date.class)) return getUtilDate();
        if (type.equals(java.util.Calendar.class)) return getCalendar();

        // java.sql
        if (type.equals(java.sql.Date.class)) return getSqlDate();
        if (type.equals(java.sql.Time.class)) return getSqlTime();
        if (type.equals(java.sql.Timestamp.class)) return getSqlTimestamp();

        throw new UnsupportedOperationException("지원하지 않는 타입");
    }
}
