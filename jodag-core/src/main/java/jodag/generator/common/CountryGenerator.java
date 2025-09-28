package jodag.generator.common;

import jodag.generator.AbstractGenerator;

import java.io.*;
import java.util.List;

public class CountryGenerator extends AbstractGenerator<String> {

    private static final CountryGenerator INSTANCE;

    private final List<String> countries;

    static {
        try {
            INSTANCE = new CountryGenerator();
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Country Generator 초기화 실패" + e);
        }
    }

    private CountryGenerator() throws FileNotFoundException {
        super("country", String.class);
        InputStream is = CountryGenerator.class.getClassLoader().getResourceAsStream("country.txt");
        if (is == null) {
            throw new FileNotFoundException("리소스를 찾을 수 없습니다: country.txt");
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
