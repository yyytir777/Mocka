package entityinstantiator.parser;

import entityinstantiator.random.RandomProvider;
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
                    .build();

            CSVParser parser = new CSVParser(reader, format);
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

    @SuppressWarnings("unchecked")
    private Object convertValue(Class<?> type, String value) {
        if (value == null || value.isEmpty()) return null;
        if (type == byte.class || type == Byte.class) return Byte.parseByte(value);
        if (type == short.class || type == Short.class) return Short.parseShort(value);
        if (type == int.class || type == Integer.class) return Integer.parseInt(value);
        if (type == long.class || type == Long.class) return Long.parseLong(value);
        if (type == float.class || type == Float.class) return Float.parseFloat(value);
        if (type == double.class || type == Double.class) return Double.parseDouble(value);
        if (type == char.class || type == Character.class) return value.charAt(0);
        if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(value);
        if (type == String.class) return value;
        if (type.isEnum()) {
            @SuppressWarnings("rawtypes")
            Class<? extends Enum> enumType = (Class<? extends Enum>) type;
            return Enum.valueOf(enumType, value);
        }
        throw new UnsupportedOperationException("Unsupported type: " + type);
    }
}
