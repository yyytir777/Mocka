package mocka.generator.datetime;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;


public class LegacyDateGenerator extends AbstractDateGenerator<Date> {

    private static final LegacyDateGenerator INSTANCE = new LegacyDateGenerator();

    private LegacyDateGenerator() {
        super("legacy_date", Date.class);
    }

    public static LegacyDateGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Date get() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * Returns a random value of the java.util type
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        if (type.equals(java.util.Date.class)) return (T) getDate();
        if (type.equals(java.util.Calendar.class)) return (T) getCalendar();
        throw new UnsupportedOperationException("Unsupported type: " + type);
    }

    /**
     * Returns a random value of the java.util.Date
     */
    public Date getDate() {
        LocalDateTime localDateTime = getRandomDate();
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Returns a random value of the java.util.Calendar
     */
    public Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate());
        return calendar;
    }
}
