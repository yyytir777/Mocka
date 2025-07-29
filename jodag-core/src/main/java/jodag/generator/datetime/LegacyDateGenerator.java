package jodag.generator.datetime;

import jodag.generator.AbstractGenerator;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class LegacyDateGenerator extends AbstractGenerator<Date> {

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

    public Date getDate() {
        return new Date();
    }

    public Calendar getCalendar() {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    }
}
