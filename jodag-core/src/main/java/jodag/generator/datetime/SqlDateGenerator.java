package jodag.generator.datetime;


import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Date;

public class SqlDateGenerator extends AbstractDateGenerator<Date> {

    private static final SqlDateGenerator INSTANCE = new SqlDateGenerator();

    private SqlDateGenerator() {
        super("sql_date", Date.class);
    }

    public static SqlDateGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Date get() {
        return getDate();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<?> type) {
        if (type.equals(java.sql.Date.class)) return (T) getDate();
        if (type.equals(java.sql.Time.class)) return (T) getTime();
        if (type.equals(java.sql.Timestamp.class)) return (T) getTimestamp();

        throw new UnsupportedOperationException("Unsupported type: " + type);
    }

    public Date getDate() {
        return Date.valueOf(getRandomDate().toLocalDate());
    }

    public Time getTime() {
        return Time.valueOf(getRandomDate().toLocalTime());
    }

    public Timestamp getTimestamp() {
        return Timestamp.valueOf(getRandomDate());
    }
}
