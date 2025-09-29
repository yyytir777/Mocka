package jodag.generator.datetime;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class LegacyDateGenerator extends AbstractDateGenerator<Date> {

    private static final LegacyDateGenerator INSTANCE = new LegacyDateGenerator();

    private LegacyDateGenerator() {
        super("legacy-date", Date.class);
    }

    public static LegacyDateGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Date get() {
        return new Date();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<?> type) {
        if (type.equals(java.util.Date.class)) return (T) getDate();
        if (type.equals(java.util.Calendar.class)) return (T) getCalendar();
        throw new UnsupportedOperationException("지원하지 않는 타입");
    }

    public Date getDate() {
        LocalDateTime localDateTime = getRandomDate();
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate());
        return calendar;
    }
}
