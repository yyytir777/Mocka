// Generated from entityinstantiator/generator/regex/Regex.g4 by ANTLR 4.13.2

    package entityinstantiator.generator.regex;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class RegexLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PIPE=1, LPAREN=2, RPAREN=3, LBRACK=4, RBRACK=5, CARET=6, STAR=7, PLUS=8, 
		QUESTION=9, DOT=10, HYTPHEN=11, QUANTITY=12, LITERAL=13, ESCAPE=14, BLANK=15;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"PIPE", "LPAREN", "RPAREN", "LBRACK", "RBRACK", "CARET", "STAR", "PLUS", 
			"QUESTION", "DOT", "HYTPHEN", "QUANTITY", "LITERAL", "ESCAPE", "BLANK"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'|'", "'('", "')'", "'['", "']'", "'^'", "'*'", "'+'", "'?'", 
			"'.'", "'-'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PIPE", "LPAREN", "RPAREN", "LBRACK", "RBRACK", "CARET", "STAR", 
			"PLUS", "QUESTION", "DOT", "HYTPHEN", "QUANTITY", "LITERAL", "ESCAPE", 
			"BLANK"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public RegexLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Regex.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u000fR\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0004\u000b8\b\u000b\u000b"+
		"\u000b\f\u000b9\u0001\u000b\u0001\u000b\u0005\u000b>\b\u000b\n\u000b\f"+
		"\u000bA\t\u000b\u0003\u000bC\b\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\r\u0001\u000e\u0004\u000eM\b\u000e\u000b\u000e"+
		"\f\u000eN\u0001\u000e\u0001\u000e\u0000\u0000\u000f\u0001\u0001\u0003"+
		"\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011"+
		"\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u0001\u0000"+
		"\u0003\u0001\u000009\u0005\u0000(+..??[]{}\u0003\u0000\t\n\r\r  U\u0000"+
		"\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000"+
		"\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000"+
		"\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r"+
		"\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d"+
		"\u0001\u0000\u0000\u0000\u0001\u001f\u0001\u0000\u0000\u0000\u0003!\u0001"+
		"\u0000\u0000\u0000\u0005#\u0001\u0000\u0000\u0000\u0007%\u0001\u0000\u0000"+
		"\u0000\t\'\u0001\u0000\u0000\u0000\u000b)\u0001\u0000\u0000\u0000\r+\u0001"+
		"\u0000\u0000\u0000\u000f-\u0001\u0000\u0000\u0000\u0011/\u0001\u0000\u0000"+
		"\u0000\u00131\u0001\u0000\u0000\u0000\u00153\u0001\u0000\u0000\u0000\u0017"+
		"5\u0001\u0000\u0000\u0000\u0019F\u0001\u0000\u0000\u0000\u001bH\u0001"+
		"\u0000\u0000\u0000\u001dL\u0001\u0000\u0000\u0000\u001f \u0005|\u0000"+
		"\u0000 \u0002\u0001\u0000\u0000\u0000!\"\u0005(\u0000\u0000\"\u0004\u0001"+
		"\u0000\u0000\u0000#$\u0005)\u0000\u0000$\u0006\u0001\u0000\u0000\u0000"+
		"%&\u0005[\u0000\u0000&\b\u0001\u0000\u0000\u0000\'(\u0005]\u0000\u0000"+
		"(\n\u0001\u0000\u0000\u0000)*\u0005^\u0000\u0000*\f\u0001\u0000\u0000"+
		"\u0000+,\u0005*\u0000\u0000,\u000e\u0001\u0000\u0000\u0000-.\u0005+\u0000"+
		"\u0000.\u0010\u0001\u0000\u0000\u0000/0\u0005?\u0000\u00000\u0012\u0001"+
		"\u0000\u0000\u000012\u0005.\u0000\u00002\u0014\u0001\u0000\u0000\u0000"+
		"34\u0005-\u0000\u00004\u0016\u0001\u0000\u0000\u000057\u0005{\u0000\u0000"+
		"68\u0007\u0000\u0000\u000076\u0001\u0000\u0000\u000089\u0001\u0000\u0000"+
		"\u000097\u0001\u0000\u0000\u00009:\u0001\u0000\u0000\u0000:B\u0001\u0000"+
		"\u0000\u0000;?\u0005,\u0000\u0000<>\u0007\u0000\u0000\u0000=<\u0001\u0000"+
		"\u0000\u0000>A\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000\u0000?@\u0001"+
		"\u0000\u0000\u0000@C\u0001\u0000\u0000\u0000A?\u0001\u0000\u0000\u0000"+
		"B;\u0001\u0000\u0000\u0000BC\u0001\u0000\u0000\u0000CD\u0001\u0000\u0000"+
		"\u0000DE\u0005}\u0000\u0000E\u0018\u0001\u0000\u0000\u0000FG\b\u0001\u0000"+
		"\u0000G\u001a\u0001\u0000\u0000\u0000HI\u0005\\\u0000\u0000IJ\t\u0000"+
		"\u0000\u0000J\u001c\u0001\u0000\u0000\u0000KM\u0007\u0002\u0000\u0000"+
		"LK\u0001\u0000\u0000\u0000MN\u0001\u0000\u0000\u0000NL\u0001\u0000\u0000"+
		"\u0000NO\u0001\u0000\u0000\u0000OP\u0001\u0000\u0000\u0000PQ\u0006\u000e"+
		"\u0000\u0000Q\u001e\u0001\u0000\u0000\u0000\u0005\u00009?BN\u0001\u0006"+
		"\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}