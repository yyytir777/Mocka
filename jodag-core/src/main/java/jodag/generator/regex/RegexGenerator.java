package jodag.generator.regex;

import jodag.generator.AbstractGenerator;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class RegexGenerator extends AbstractGenerator<String> {

    private static final RegexGenerator INSTANCE = new RegexGenerator();
    private final RegexStringVisitor regexStringVisitor;

    private RegexGenerator() {
        super("regex", String.class);
        this.regexStringVisitor = new RegexStringVisitor(this.randomProvider);
    }

    public static RegexGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String get() {
        throw new UnsupportedOperationException();
    }

    public String get(String regex) {
        CodePointCharStream stream = CharStreams.fromString(regex);
        RegexLexer lexer = new RegexLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RegexParser parser = new RegexParser(tokens);
        ParseTree tree = parser.regex();
        return regexStringVisitor.visit(tree);
    }
}
