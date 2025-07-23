package jodag.generator.common;


import jodag.generator.AbstractGenerator;
import jodag.generator.StringGenerator;



public class EmailGenerator extends AbstractGenerator<String> {

    private static final EmailGenerator INSTANCE =  new EmailGenerator();

    private EmailGenerator() {
        super("email", String.class);
    }

    public static EmailGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String get() {
        String username = StringGenerator.getInstance().get(7, 10);
        String domain = StringGenerator.getInstance().get(3, 7);
        String tld = StringGenerator.getInstance().get(2, 3);
        return username + "@" + domain + "." + tld;
    }
}
