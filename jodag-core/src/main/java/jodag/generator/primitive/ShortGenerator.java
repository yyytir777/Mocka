package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

public class ShortGenerator extends AbstractGenerator<Short> {

    private static ShortGenerator INSTANCE;

    private ShortGenerator() {
        super("short", Short.class);
    }

    public static synchronized ShortGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShortGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Short get() {
        return randomProvider.nextShort();
    }
}
