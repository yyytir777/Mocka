package jodag.generator.datetime;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommonDateGenerator Test Code")
public class CommonDateGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final DateTimeGenerator dateTimeGenerator = generatorFactory.asDateTime();
    private final LegacyDateGenerator legacyDateGenerator = generatorFactory.asLegacyDate();
    private final SqlDateGenerator sqlDateGenerator = generatorFactory.asSqlDate();

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
}
