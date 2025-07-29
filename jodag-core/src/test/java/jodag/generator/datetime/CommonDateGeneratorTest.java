package jodag.generator.datetime;

import jodag.generator.GeneratorFactory;
import jodag.generator.array.ArrayGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommonDateGenerator 테스트")
public class CommonDateGeneratorTest {

    private final CommonDateGenerator commonDateGenerator = GeneratorFactory.dateTime();

    @Test
    @DisplayName("GeneratorFactory를 통해 가져온 CommonDateGenerator가 해당 클래스인지 확인")
    public void get_instance() {

        assertThat(commonDateGenerator).isInstanceOf(CommonDateGenerator.class);
    }

    @Test
    @DisplayName("ArrayGenerator는 싱글톤으로 관리")
    void arrayGenerator_is_singleton() {
        CommonDateGenerator commonDateGenerator1 = GeneratorFactory.dateTime();

        assertThat(commonDateGenerator).isSameAs(commonDateGenerator1);
    }

    @Test
    @DisplayName("LocalDate 반환")
    void get_localDate() {
        LocalDate localDate = commonDateGenerator.getLocalDate();
        System.out.println("localDate = " + localDate);
        assertThat(localDate).isNotNull();
    }

    @Test
    @DisplayName("LocalTime 반환")
    void get_localTime() {
        LocalTime localTime = commonDateGenerator.getLocalTime();
        System.out.println("localTime = " + localTime);
        assertThat(localTime).isNotNull();
    }

    @Test
    @DisplayName("LocalDateTime 반환")
    void get_localDateTime() {
        LocalDateTime localDateTime = commonDateGenerator.getLocalDateTime();
        System.out.println("localDateTime = " + localDateTime);
        assertThat(localDateTime).isNotNull();
    }

    @Test
    @DisplayName("OffsetTime 반환")
    void get_offSetTime() {
        OffsetTime offsetTime = commonDateGenerator.getOffsetTime();
        System.out.println("offsetTime = " + offsetTime);
        assertThat(offsetTime).isNotNull();
    }

    @Test
    @DisplayName("OffsetDateTime 반환")
    void get_offSetDateTime() {
        OffsetDateTime offsetDateTime = commonDateGenerator.getOffsetDateTime();
        System.out.println("offsetDateTime = " + offsetDateTime);
        assertThat(offsetDateTime).isNotNull();
    }

    @Test
    @DisplayName("Instant 반환")
    void get_instant() {
        Instant instant = commonDateGenerator.getInstant();
        System.out.println("instant = " + instant);
        assertThat(instant).isNotNull();
    }

    @Test
    @DisplayName("Date 반환 (java.util)")
    void get_java_util_date() {
        Date utilDate = commonDateGenerator.getUtilDate();
        System.out.println("utilDate = " + utilDate);
        assertThat(utilDate).isNotNull();
    }

    @Test
    @DisplayName("Calendar 반환")
    void get_calendar() {
        Calendar calendar = commonDateGenerator.getCalendar();
        System.out.println("calendar = " + calendar);
        assertThat(calendar).isNotNull();
    }

    @Test
    @DisplayName("Date 반환 (java.sql)")
    void get_java_sql_date() {
        java.sql.Date sqlDate = commonDateGenerator.getSqlDate();
        System.out.println("sqlDate = " + sqlDate);
        assertThat(sqlDate).isNotNull();
    }

    @Test
    @DisplayName("SqlTime 반환")
    void get_sql_time() {
        java.sql.Time sqlTime = commonDateGenerator.getSqlTime();
        System.out.println("sqlTime = " + sqlTime);
        assertThat(sqlTime).isNotNull();
    }

    @Test
    @DisplayName("SqlTimestamp 반환")
    void get_sql_timestamp() {
        java.sql.Timestamp sqlTimestamp = commonDateGenerator.getSqlTimestamp();
        System.out.println("sqlTimestamp = " + sqlTimestamp);
        assertThat(sqlTimestamp).isNotNull();
    }
}
