package entityinstantiator.generator.regex;

import entityinstantiator.generator.AbstractGenerator;
import org.antlr.v4.runtime.*;
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
        RegexLexer lexer = getRegexLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RegexParser parser = getRegexParser(tokens);

        try {
            ParseTree tree = parser.regex();
            return regexStringVisitor.visit(tree);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("Regex parsing failed: " + regex, e);
        }
    }

    private static RegexLexer getRegexLexer(CodePointCharStream stream) {
        RegexLexer lexer = new RegexLexer(stream);
        lexer.removeErrorListeners();

        // throw IllegalArgumentException when invalid regex grammar.
        lexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalArgumentException(
                        "Invalid regex syntax (lexer error) at line " + line + ":" + charPositionInLine + " → " + msg
                );
            }
        });
        return lexer;
    }

    private static RegexParser getRegexParser(CommonTokenStream tokens) {
        return new RegexParser(tokens);
    }
}
