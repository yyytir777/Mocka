package mocka.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import mocka.random.RandomProvider;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class JsonFileParser implements FileParser {

    private static final JsonFileParser INSTANCE = new JsonFileParser();
    private final RandomProvider randomProvider = RandomProvider.getInstance();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonFileParser() {}

    public static JsonFileParser getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <T> T parse(InputStream inputStream, Class<T> clazz) {
        try {
            if (inputStream == null || inputStream.available() == 0) {
                throw new RuntimeException("Empty JSON input: no content to parse.");
            }

            Object root = MAPPER.readValue(inputStream, Object.class);
            Object result;
            if (root instanceof List<?> list) {
                result = list.get(randomProvider.getNextIdx(list.size()));
            } else {
                return null;
            }

            Map<String, Object> fieldMap = (Map<String, Object>) result;
            T instance = clazz.getDeclaredConstructor().newInstance();
            for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                try {
                    Field field = clazz.getDeclaredField(key);
                    field.setAccessible(true);

                    if(value != null) {
                        field.set(instance, convertValue(field.getType(), value));
                    }
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }

            return instance;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON file: " + e.getMessage(), e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
