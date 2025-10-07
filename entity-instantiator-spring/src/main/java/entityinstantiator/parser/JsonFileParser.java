package entityinstantiator.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import entityinstantiator.random.RandomProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonFileParser implements FileParser {

    private static final JsonFileParser INSTANCE = new JsonFileParser();
    private final RandomProvider randomProvider = RandomProvider.getInstance();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonFileParser() {}

    public static JsonFileParser getInstance() {
        return INSTANCE;
    }

    public <T> T parse(InputStream inputStream, Class<T> clazz) {
        try {
            if (inputStream == null || inputStream.available() == 0) {
                throw new RuntimeException("Empty JSON input: no content to parse.");
            }

            JsonParser parser = MAPPER.createParser(inputStream);
            if (parser.nextToken() == null) {
                throw new RuntimeException("Empty JSON file: no valid JSON content found.");
            }

            if (parser.currentToken().isStructStart()
                    && parser.currentToken().name().equals("START_ARRAY")) {
                List<T> list = MAPPER.readValue(parser,
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));

                if (list.isEmpty()) throw new RuntimeException("Empty JSON input: no content to parse.");
                return list.get(randomProvider.getNextIdx(list.size()));
            }

            return MAPPER.readValue(parser, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON file: " + e.getMessage(), e);
        }
    }
}
