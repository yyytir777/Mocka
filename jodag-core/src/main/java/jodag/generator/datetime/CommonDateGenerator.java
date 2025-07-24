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
}
