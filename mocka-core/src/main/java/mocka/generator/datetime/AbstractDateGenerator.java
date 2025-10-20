package mocka.generator.datetime;

import mocka.generator.AbstractGenerator;

import java.time.*;

public abstract class AbstractDateGenerator<T> extends AbstractGenerator<T> {

    public AbstractDateGenerator(String name, Class<T> type) {
        super(name, type);
    }

    protected LocalDateTime getRandomDate() {
        long start = LocalDate.of(1970, 1, 1).toEpochDay();
        long end = LocalDate.now().toEpochDay();
        long randomDay = randomProvider.getLong(start, end);
        int randomSecond = randomProvider.getNextIdx(24 * 60 * 60);
        return LocalDate.ofEpochDay(randomDay).atTime(LocalTime.ofSecondOfDay(randomSecond));
    }

    protected ZoneOffset getRandomOffset() {
        return ZoneOffset.ofHours(randomProvider.getInt(-18, 18));
    }
}
