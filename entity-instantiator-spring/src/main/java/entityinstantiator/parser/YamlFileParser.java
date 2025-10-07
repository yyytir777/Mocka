package entityinstantiator.parser;

import java.io.InputStream;

public class YamlFileParser implements FileParser {

    private static final YamlFileParser INSTANCE = new YamlFileParser();

    private YamlFileParser() {}

    public static YamlFileParser getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> T parse(InputStream inputStream, Class<T> clazz) {
        return null;
    }
}
