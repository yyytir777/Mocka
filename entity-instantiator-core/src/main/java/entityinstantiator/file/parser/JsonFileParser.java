package entityinstantiator.file.parser;

import java.io.InputStream;

public class JsonFileParser implements FileParser {

    private static final JsonFileParser INSTANCE = new JsonFileParser();

    private JsonFileParser() {}

    public static JsonFileParser getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> T parse(InputStream inputStream, Class<T> clazz) {
        return null;
    }
}
