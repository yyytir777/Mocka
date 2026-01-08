package mocka.orm.parser;

import mocka.core.random.RandomProvider;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CsvFileParser implements FileParser {

    private static final CsvFileParser INSTANCE = new CsvFileParser();
    private final RandomProvider randomProvider = RandomProvider.getInstance();

    private CsvFileParser() {}

    public static CsvFileParser getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> T parse(InputStream inputStream, Class<T> clazz) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            CSVFormat format = CSVFormat.Builder.create()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setIgnoreHeaderCase(true)
                    .setTrim(true)
                    .get();

            CSVParser parser = CSVParser.builder()
                    .setReader(reader)
                    .setFormat(format).get();

            List<T> list = new ArrayList<>();
            for (CSVRecord record : parser) {
                T instance = clazz.getDeclaredConstructor().newInstance();

                record.toMap().forEach((key, value) -> {
                    try {
                        Field field = clazz.getDeclaredField(key);
                        field.setAccessible(true);
                        field.set(instance, convertValue(field.getType(), value));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
                list.add(instance);
            }

            if(list.isEmpty()) throw new RuntimeException("Empty CSV file: no valid CSV content found.");
            return list.get(randomProvider.getNextIdx(list.size()));

        } catch (IOException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException("Failed to parse CSV file into " + clazz.getSimpleName() + ": " + e.getMessage(), e);
        }
    }
}
