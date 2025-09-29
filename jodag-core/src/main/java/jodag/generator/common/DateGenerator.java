package jodag.generator.common;

import jodag.generator.AbstractGenerator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateGenerator extends AbstractGenerator<Date> {

    private static final DateGenerator INSTANCE = new DateGenerator();

    private DateGenerator() {
        super("date", Date.class);
    }

    public static DateGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Date get() {
        throw new UnsupportedOperationException();
    }

    public String get(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        LocalDate start = LocalDate.of(1970, 1, 1);
        long days = ChronoUnit.DAYS.between(start, LocalDate.now());
        long randomDays = randomProvider.getNextIdx(days);
        LocalDate randomDate = start.plusDays(randomDays);
        return randomDate.format(formatter);
    }
}
