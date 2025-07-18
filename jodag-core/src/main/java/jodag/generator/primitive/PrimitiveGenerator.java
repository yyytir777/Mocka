package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

public class PrimitiveGenerator extends AbstractGenerator<Object> {

    private static PrimitiveGenerator INSTANCE;

    private PrimitiveGenerator() {
        super("primitive", Object.class);
    }

    public static synchronized PrimitiveGenerator getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new PrimitiveGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Object get() {
        return "primitive";
    }

    public Boolean getBoolean() {
        return true;
    }

    public Byte getByte() {
        return Byte.MAX_VALUE;
    }

    public Short getShort() {
        return Short.MAX_VALUE;
    }

    public Character getCharacter() {
        return Character.MAX_VALUE;
    }

    public Integer getInteger() {
        return randomProvider.nextInteger();
    }

    public Long getLong() {
        return randomProvider.nextLong();
    }

    public Float getFloat() {
        return Float.MAX_VALUE;
    }

    public Double getDouble() {
        return Double.MAX_VALUE;
    }
}
