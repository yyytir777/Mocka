package mocka.core.generator.regex;


import mocka.core.generator.primitive.CharacterGenerator;
import mocka.core.generator.primitive.StringGenerator;
import mocka.core.random.RandomProvider;
import mockacore.generator.regex.RegexBaseVisitor;
import mockacore.generator.regex.RegexParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 */
public class RegexStringVisitor extends RegexBaseVisitor<String> {

    private final RandomProvider random;
    private final CharacterGenerator characterGenerator;
    private final StringGenerator stringGenerator;
    private static final String DIGITS = "0123456789";
    private static final String WORD_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";
    private static final String WHITESPACES = " \t\n\r\f";
    private static final String NON_DIGITS;
    private static final String NON_WORD_CHARS;
    private static final String NON_WHITESPACES;

    static {
        StringBuilder nonDigits = new StringBuilder();
        StringBuilder nonWordChars = new StringBuilder();
        StringBuilder nonWordSpaces = new StringBuilder();
        for (char c = 32; c <= 127; c++) {
            if(!Character.isDigit(c)) nonDigits.append(c);
            if(WORD_CHARS.indexOf(c) == -1)  nonWordChars.append(c);
            if(!Character.isWhitespace(c)) nonWordSpaces.append(c);
        }

        NON_DIGITS = nonDigits.toString();
        NON_WORD_CHARS = nonWordChars.toString();
        NON_WHITESPACES = nonWordSpaces.toString();
    }

    public RegexStringVisitor(RandomProvider random) {
        this.random = random;
        this.characterGenerator = CharacterGenerator.getInstance();
        this.stringGenerator = StringGenerator.getInstance();
    }

    @Override
    public String visitRegex(RegexParser.RegexContext ctx) {
        if (ctx.expr().size() == 1) {
            return visit(ctx.expr(0));
        } else {
            int choice = random.getNextIdx(ctx.expr().size());
            return visit(ctx.expr(choice));
        }
    }

    @Override
    public String visitExpr(RegexParser.ExprContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (RegexParser.ElementContext el : ctx.element()) {
            sb.append(visit(el));
        }
        return sb.toString();
    }

    @Override
    public String visitElement(RegexParser.ElementContext ctx) {
        if (ctx.quantifier() != null) {
            return visit(ctx.quantifier());
        } else {
            return visit(ctx.atom());
        }
    }

    @Override
    public String visitAtom(RegexParser.AtomContext ctx) {
        if (ctx.LITERAL() != null) {
            return ctx.LITERAL().getText();
        } else if (ctx.ESCAPE() != null) {
            return getEscape(ctx.ESCAPE().getText());
        } else if (ctx.group() != null) {
            return visit(ctx.group());
        } else if (ctx.charClass() != null) {
            return visit(ctx.charClass());
        } else if (ctx.DOT() != null) {
            return String.valueOf((char) (32 + random.getNextIdx(95)));
        } else if (ctx.HYTPHEN() != null) {
            return "-";
        }
        return "";
    }

    private String getEscape(String text) {
        return String.valueOf(switch (text) {
            case "\\d" -> characterGenerator.getCharacter(DIGITS);
            case "\\D" -> characterGenerator.getCharacter(NON_DIGITS);
            case "\\w" -> characterGenerator.getCharacter(WORD_CHARS);
            case "\\W" -> characterGenerator.getCharacter(NON_WORD_CHARS);
            case "\\s" -> characterGenerator.getCharacter(WHITESPACES);
            case "\\S" -> characterGenerator.getCharacter(NON_WHITESPACES);

            case "\\." -> ".";
            case "\\+" -> "+";
            case "\\*" -> "*";
            case "\\?" -> "?";
            case "\\(" -> "(";
            case "\\)" -> ")";
            case "\\[" -> "[";
            case "\\]" -> "]";
            case "\\{" -> "{";
            case "\\}" -> "}";
            case "\\|" -> "|";
            case "\\^" -> "^";
            case "\\-" -> "-";

            default -> "";
        });
    }

    @Override
    public String visitGroup(RegexParser.GroupContext ctx) {
        return visit(ctx.regex());
    }

    /**
     * + : 1번 이상
     * * : 0번 이상
     * ? : 0 또는 1번
     * {n} : n번 반복
     * {n,m} : n ~ m 번 반복
     * {n,} : n번 이상 반복
     */
    @Override
    public String visitQuantifier(RegexParser.QuantifierContext ctx) {
        int min = 0, max = 0;
        if (ctx.STAR() != null) {
            max = 5;
        } else if (ctx.PLUS() != null) {
            min = 1; max = 5;
        } else if (ctx.QUESTION() != null) {
            max = 1;
        } else if (ctx.QUANTITY() != null) {
            String quantity = ctx.getText();
            if (quantity.matches("\\{\\d+}")) {
                int n = Integer.parseInt(quantity.replaceAll("[{}]", ""));
                min = max = n;
            } else if (quantity.matches("\\{\\d+,}")) {
                int n = Integer.parseInt(quantity.replaceAll("[{}]", "").replace(",", ""));
                min = n;
                max = n + 5;
            } else if (Pattern.matches("\\{\\d+,\\d+}", quantity)) {
                String[] parts = quantity.replaceAll("[{}]", "").split(",");
                min = Integer.parseInt(parts[0]);
                max = Integer.parseInt(parts[1]);
            }
        } else {
            return "";
        }

        int cnt = random.getInt(min, max + 1);
        RegexParser.ElementContext parent = (RegexParser.ElementContext) ctx.getParent();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cnt; i++) {
            sb.append(visit(parent.atom()));
        }
        return sb.toString();
    }

    @Override
    public String visitCharRange(RegexParser.CharRangeContext ctx) {
        char start = ctx.LITERAL(0).getText().charAt(0);
        char end = ctx.LITERAL(1).getText().charAt(0);

        int diff = end - start + 1;
        return String.valueOf((char) (start + random.getNextIdx(diff)));
    }

    /**
     * CharClass: <br>
     * 1. Selects one of the characters inside the brackets. e.g. [abc] -> c <br>
     * 2. Selects a character within a specified range. e.g. [a-z] -> any character from 'a' to 'z' <br>
     * 3. Negation: selects a character that is not in the brackets. e.g. [^a] -> any character except 'a' <br>
     */
    @Override
    public String visitCharClass(RegexParser.CharClassContext ctx) {
        if (ctx.CARET() != null) {
            return visitCharClassExclude(ctx);
        }

        List<String> candidates = new ArrayList<>();
        if (ctx.classAtom() != null) {
            for (RegexParser.ClassAtomContext classAtomContext : ctx.classAtom()) {
                candidates.add(visit(classAtomContext));
            }
        }
        return candidates.get(random.getNextIdx(candidates.size()));
    }

    private String visitCharClassExclude(RegexParser.CharClassContext ctx) {
        Set<Character> exclude = new HashSet<>();

        for (RegexParser.ClassAtomContext atomCxt : ctx.classAtom()) {
            String candidates = getAll(atomCxt);
            for (char c : candidates.toCharArray()) {
                exclude.add(c);
            }
        }
        return String.valueOf(characterGenerator.getCharacterNotIn(exclude));
    }

    private String getAll(RegexParser.ClassAtomContext ctx) {
        if (ctx.charRange() != null) {
            char start = ctx.charRange().LITERAL(0).getText().charAt(0);
            char end = ctx.charRange().LITERAL(1).getText().charAt(0);
            return stringGenerator.getAllCharacter(start, end);
        } else if (ctx.LITERAL() != null) {
            return ctx.LITERAL().getText();
        } else if (ctx.ESCAPE() != null) {
            return getAllEscape(ctx.ESCAPE().getText());
        } else if (ctx.CARET() != null) {
            return "^";
        }
        return "";
    }

    private String getAllEscape(String text) {
        return switch (text) {
            case "\\d" -> DIGITS;
            case "\\D" -> NON_DIGITS;
            case "\\w" -> WORD_CHARS;
            case "\\W" -> NON_WORD_CHARS;
            case "\\s" -> WHITESPACES;
            case "\\S" -> NON_WHITESPACES;
            default -> "";
        };
    }

    @Override
    public String visitClassAtom(RegexParser.ClassAtomContext ctx) {
        if (ctx.charRange() != null) {
            return visitCharRange(ctx.charRange());
        } else if (ctx.LITERAL() != null) {
            return ctx.getText();
        } else if (ctx.ESCAPE() != null) {
            return getEscape(ctx.ESCAPE().getText());
        } else if (ctx.CARET() != null) {
            return "^";
        }
        return "";
    }
}
