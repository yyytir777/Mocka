package entityinstantiator.generator.datetime;

import entityinstantiator.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CommonDateGenerator Test Code")
public class CommonDateGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final DateTimeGenerator dateTimeGenerator = generatorFactory.asDateTime();
    private final LegacyDateGenerator legacyDateGenerator = generatorFactory.asLegacyDate();
    private final SqlDateGenerator sqlDateGenerator = generatorFactory.asSqlDate();

    @Test
    @DisplayName("CommonDateGenerator is managed as a singleton")
    void emailGenerator_is_singleton() {
        DateTimeGenerator newDateTimeGenerator = generatorFactory.asDateTime();
        assertThat(newDateTimeGenerator).isSameAs(dateTimeGenerator);

        LegacyDateGenerator newLegacyDateGenerator = generatorFactory.asLegacyDate();
        assertThat(newLegacyDateGenerator).isSameAs(legacyDateGenerator);

        SqlDateGenerator newSqlDateGenerator = generatorFactory.asSqlDate();
        assertThat(newSqlDateGenerator).isSameAs(sqlDateGenerator);
    }

    @Test
    @DisplayName("dateTimeGenerator.get() method returns LocalDateTime")
    void get_method_returns_LocalDateTime() {
        Temporal temporal = dateTimeGenerator.get();
        System.out.println("temporal = " + temporal);
        assertThat(temporal).isInstanceOf(LocalDateTime.class);
    }

    @Test
    @DisplayName("dateTimeGenerator.get(Class<?> type) returns all date type the class supports")
    void get_method_returns_all_date_type_dateTimeGenerator_supports() {
        Temporal temporal_localDate = dateTimeGenerator.get(LocalDate.class);
        assertThat(temporal_localDate).isNotNull().isInstanceOf(LocalDate.class);

        Temporal temporal_localTime = dateTimeGenerator.get(LocalTime.class);
        assertThat(temporal_localTime).isNotNull().isInstanceOf(LocalTime.class);

        Temporal temporal_localDateTime = dateTimeGenerator.get(LocalDateTime.class);
        assertThat(temporal_localDateTime).isNotNull().isInstanceOf(LocalDateTime.class);

        Temporal temporal_offSetTime = dateTimeGenerator.get(OffsetTime.class);
        assertThat(temporal_offSetTime).isNotNull().isInstanceOf(OffsetTime.class);

        Temporal temporal_offSetDateTime = dateTimeGenerator.get(OffsetDateTime.class);
        assertThat(temporal_offSetDateTime).isNotNull().isInstanceOf(OffsetDateTime.class);

        Temporal temporal_instant = dateTimeGenerator.get(Instant.class);
        assertThat(temporal_instant).isNotNull().isInstanceOf(Instant.class);

        assertThatThrownBy(() -> dateTimeGenerator.get(String.class))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("return LocalDate")
    void get_localDate() {
        LocalDate localDate = dateTimeGenerator.getLocalDate();
        System.out.println("localDate = " + localDate);
        assertThat(localDate).isNotNull().isInstanceOf(LocalDate.class);
    }

    @Test
    @DisplayName("return LocalTime")
    void get_localTime() {
        LocalTime localTime = dateTimeGenerator.getLocalTime();
        System.out.println("localTime = " + localTime);
        assertThat(localTime).isNotNull().isInstanceOf(LocalTime.class);
    }

    @Test
    @DisplayName("return LocalDateTime")
    void get_localDateTime() {
        LocalDateTime localDateTime = dateTimeGenerator.getLocalDateTime();
        System.out.println("localDateTime = " + localDateTime);
        assertThat(localDateTime).isNotNull().isInstanceOf(LocalDateTime.class);
    }

    @Test
    @DisplayName("return OffsetTime")
    void get_offSetTime() {
        OffsetTime offsetTime = generatorFactory.asDateTime().getOffsetTime();
        System.out.println("offsetTime = " + offsetTime);
        assertThat(offsetTime).isNotNull().isInstanceOf(OffsetTime.class);
    }

    @Test
    @DisplayName("return OffsetDateTime")
    void get_offSetDateTime() {
        OffsetDateTime offsetDateTime = dateTimeGenerator.getOffsetDateTime();
        System.out.println("offsetDateTime = " + offsetDateTime);
        assertThat(offsetDateTime).isNotNull().isInstanceOf(OffsetDateTime.class);
    }

    @Test
    @DisplayName("return Instant")
    void get_instant() {
        Instant instant = dateTimeGenerator.getInstant();
        System.out.println("instant = " + instant);
        assertThat(instant).isNotNull().isInstanceOf(Instant.class);
    }

    @Test
    @DisplayName("legacyDateGenerator.get() method returns Date")
    void get_method_returns_Date() {
        Date date = legacyDateGenerator.get();
        assertThat(date).isNotNull().isInstanceOf(Date.class);
    }

    @Test
    @DisplayName("legacyDateGenerator.get(Class<?> type) returns all date type the class supports")
    void get_method_returns_all_date_type_legacyDateGenerator_supports() {
        Object date = legacyDateGenerator.get(Date.class);
        assertThat(date).isNotNull().isInstanceOf(Date.class);

        Object calendar = legacyDateGenerator.get(Calendar.class);
        assertThat(calendar).isNotNull().isInstanceOf(Calendar.class);


        assertThatThrownBy(() -> legacyDateGenerator.get(String.class))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("throws UnsupportedOperationException when invoke get(Class<T> type) method with a non-java.util type")
    void throws_UnsupportedOperationException_when_not_supported_type_in_legacyDate() {
        assertThatThrownBy(() -> legacyDateGenerator.get(String.class))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("return Date (java.util)")
    void get_java_util_date() {
        Date utilDate = legacyDateGenerator.getDate();
        System.out.println("utilDate = " + utilDate);
        assertThat(utilDate).isNotNull().isInstanceOf(Date.class);
    }

    @Test
    @DisplayName("return Calendar")
    void get_calendar() {
        Calendar calendar = legacyDateGenerator.getCalendar();
        System.out.println("calendar = " + calendar);
        assertThat(calendar).isNotNull().isInstanceOf(Calendar.class);
    }

    @Test
    @DisplayName("sqlDateGenerator.get() method returns java.sql.Date")
    void get_method_returns_sql_date() {
        java.sql.Date date = sqlDateGenerator.get();
        assertThat(date).isNotNull().isInstanceOf(java.sql.Date.class);
    }

    @Test
    @DisplayName("sqlDateGenerator.get(Class<?> type) returns all date type sqlDateGenerator supports")
    void get_method_returns_all_date_type_sqlDateGenerator_supports() {
        Object date = sqlDateGenerator.get(java.sql.Date.class);
        assertThat(date).isNotNull().isInstanceOf(java.sql.Date.class);

        Object time = sqlDateGenerator.get(java.sql.Time.class);
        assertThat(time).isNotNull().isInstanceOf(java.sql.Time.class);

        Object timestamp = sqlDateGenerator.get(Timestamp.class);
        assertThat(timestamp).isNotNull().isInstanceOf(java.sql.Timestamp.class);
    }

    @Test
    @DisplayName("return Date (java.sql)")
    void get_java_sql_date() {
        java.sql.Date sqlDate = sqlDateGenerator.getDate();
        System.out.println("sqlDate = " + sqlDate);
        assertThat(sqlDate).isNotNull().isInstanceOf(java.sql.Date.class);
    }

    @Test
    @DisplayName("return SqlTime")
    void get_sql_time() {
        java.sql.Time sqlTime = sqlDateGenerator.getTime();
        System.out.println("sqlTime = " + sqlTime);
        assertThat(sqlTime).isNotNull().isInstanceOf(java.sql.Time.class);
    }

    @Test
    @DisplayName("return SqlTimestamp")
    void get_sql_timestamp() {
        java.sql.Timestamp sqlTimestamp = sqlDateGenerator.getTimestamp();
        System.out.println("sqlTimestamp = " + sqlTimestamp);
        assertThat(sqlTimestamp).isNotNull().isInstanceOf(java.sql.Timestamp.class);
    }

    @Test
    @DisplayName("throws UnsupportedOperationException when invoke get(Class<T> type) method with a non-java.sql type")
    void throws_UnsupportedOperationException_when_not_supported_type_in_sqlDate() {
        assertThatThrownBy(() -> sqlDateGenerator.get(String.class))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
