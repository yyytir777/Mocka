package entityinstantiator.parser;

import entityinstantiator.random.RandomProvider;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class YamlFileParser implements FileParser {

    private static final YamlFileParser INSTANCE = new YamlFileParser();
    private final RandomProvider randomProvider = RandomProvider.getInstance();
    private final Yaml yaml = new Yaml();

    private YamlFileParser() {}

    public static YamlFileParser getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <T> T parse(InputStream inputStream, Class<T> clazz) {
        try {
            Object root = yaml.load(inputStream);
            Object value;

            if (root instanceof List<?> list) {
                value = list.get(randomProvider.getNextIdx(list.size()));
            } else if (root instanceof Map<?, ?> map) {
                Optional<?> innerList = map.values().stream()
                        .filter(v -> v instanceof List<?>)
                        .findFirst();

                if (innerList.isPresent()) {
                    List<?> list = (List<?>) innerList.get();
                    value = list.get(randomProvider.getNextIdx(list.size()));
                } else {
                    value = root;
                }
            } else {
                throw new RuntimeException("Unsupported YAML structure.");
            }

            Map<String, Object> fieldMap = (Map<String, Object>) value;
            T instance = clazz.getDeclaredConstructor().newInstance();

            for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
                String key = entry.getKey();
                Object val = entry.getValue();

                try {
                    Field field = clazz.getDeclaredField(key);
                    field.setAccessible(true);
                    if (val != null) {
                        field.set(instance, convertValue(field.getType(), String.valueOf(val)));
                    }
                } catch (NoSuchFieldException ignored) {}
            }

            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
