package mocka.core.generator.common;

import mocka.core.generator.AbstractGenerator;
import mocka.core.generator.primitive.IntegerGenerator;

import java.net.URI;
import java.net.URL;
import java.util.*;

public class UtilGenerator extends AbstractGenerator<String> {

    private static final UtilGenerator INSTANCE = new UtilGenerator();
    private static final Locale[] AVAILABLE_LOCALES = Locale.getAvailableLocales();
    private static final List<Currency> AVAILABLE_CURRENCIES = new ArrayList<>(Currency.getAvailableCurrencies());
    private final IntegerGenerator intGenerator = IntegerGenerator.getInstance();

    public UtilGenerator() {
        super("util", String.class);
    }

    public static UtilGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String get() {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    public URI getUri() {
        // TODO 추후 구현
        return null;
    }

    public URL getUrl() {
        // TODO 추후 구현
        return null;
    }

    public Locale getLocale() {
        return AVAILABLE_LOCALES[intGenerator.getNextInteger(AVAILABLE_LOCALES.length)];
    }

    public Currency getCurrency() {
        return AVAILABLE_CURRENCIES.get(intGenerator.getNextInteger(AVAILABLE_CURRENCIES.size()));
    }
}
