package entityinstantiator.parser;

import java.io.InputStream;

public class CsvFileParser implements FileParser {

    private static final CsvFileParser INSTANCE = new CsvFileParser();

    private CsvFileParser() {}

    public static CsvFileParser getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> T parse(InputStream inputStream, Class<T> clazz) {
        return null;
    }
}
