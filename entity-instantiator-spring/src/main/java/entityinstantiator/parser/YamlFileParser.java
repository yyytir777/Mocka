package entityinstantiator.parser;

import entityinstantiator.random.RandomProvider;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class YamlFileParser implements FileParser {

    private static final YamlFileParser INSTANCE = new YamlFileParser();
    private final RandomProvider randomProvider = RandomProvider.getInstance();
    private final Yaml yaml = new Yaml();

    private YamlFileParser() {}

    public static YamlFileParser getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> T parse(InputStream inputStream, Class<T> clazz) {
        Map<String, Object> root = yaml.load(inputStream);

        Object value = root.get("entity");
        if (value == null) {
            throw new RuntimeException("No matching key found for " + clazz.getSimpleName());
        }

        Object target;
        if (value instanceof List<?> list) {
            target = list.get(randomProvider.getNextIdx(list.size()));
        } else {
            target = value;
        }

        String yamlString = yaml.dump(target);
        return yaml.loadAs(yamlString, clazz);
    }
}
