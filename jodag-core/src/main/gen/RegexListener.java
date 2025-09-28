// Generated from /Users/wonjae/Java/jodag/jodag-core/src/main/antlr/jodag/generator/regex/Regex.g4 by ANTLR 4.13.2

    package jodag.generator.regex;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RegexParser}.
 */
public interface RegexListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RegexParser#regex}.
	 * @param ctx the parse tree
	 */
	void enterRegex(RegexParser.RegexContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#regex}.
	 * @param ctx the parse tree
	 */
	void exitRegex(RegexParser.RegexContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(RegexParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(RegexParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#element}.
	 * @param ctx the parse tree
	 */
	void enterElement(RegexParser.ElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#element}.
	 * @param ctx the parse tree
	 */
	void exitElement(RegexParser.ElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(RegexParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(RegexParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#group}.
	 * @param ctx the parse tree
	 */
	void enterGroup(RegexParser.GroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#group}.
	 * @param ctx the parse tree
	 */
	void exitGroup(RegexParser.GroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#charClass}.
	 * @param ctx the parse tree
	 */
	void enterCharClass(RegexParser.CharClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#charClass}.
	 * @param ctx the parse tree
	 */
	void exitCharClass(RegexParser.CharClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#classAtom}.
	 * @param ctx the parse tree
	 */
	void enterClassAtom(RegexParser.ClassAtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#classAtom}.
	 * @param ctx the parse tree
	 */
	void exitClassAtom(RegexParser.ClassAtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#charRange}.
	 * @param ctx the parse tree
	 */
	void enterCharRange(RegexParser.CharRangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#charRange}.
	 * @param ctx the parse tree
	 */
	void exitCharRange(RegexParser.CharRangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#quantifier}.
	 * @param ctx the parse tree
	 */
	void enterQuantifier(RegexParser.QuantifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#quantifier}.
	 * @param ctx the parse tree
	 */
	void exitQuantifier(RegexParser.QuantifierContext ctx);
}