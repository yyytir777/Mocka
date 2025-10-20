package mocka.generator.common;

import mocka.generator.AbstractGenerator;

import java.io.*;
import java.util.List;

public class CountryGenerator extends AbstractGenerator<String> {

    private static final CountryGenerator INSTANCE;

    private final List<String> countries;

    static {
        try {
            INSTANCE = new CountryGenerator();
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed initialize Country Generator" + e);
        }
    }

    private CountryGenerator() throws FileNotFoundException {
        super("country", String.class);
        InputStream is = CountryGenerator.class.getClassLoader().getResourceAsStream("country.txt");
        if (is == null) {
            throw new FileNotFoundException("Couldn't load resources : country.txt");
        }

        this.countries = new BufferedReader(new InputStreamReader(is))
                .lines().toList();
    }

    public static CountryGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String get() {
        return countries.get(randomProvider.getInt(countries.size()));
    }
}
