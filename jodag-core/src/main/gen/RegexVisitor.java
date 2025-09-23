// Generated from /Users/wonjae/Java/jodag/jodag-core/src/main/antlr/jodag/generator/regex/Regex.g4 by ANTLR 4.13.2

    package jodag.generator.regex;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RegexParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RegexVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link RegexParser#regex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegex(RegexParser.RegexContext ctx);
	/**
	 * Visit a parse tree produced by {@link RegexParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(RegexParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link RegexParser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement(RegexParser.ElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link RegexParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(RegexParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link RegexParser#group}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroup(RegexParser.GroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link RegexParser#charClass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharClass(RegexParser.CharClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link RegexParser#classAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassAtom(RegexParser.ClassAtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link RegexParser#charRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharRange(RegexParser.CharRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link RegexParser#quantifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuantifier(RegexParser.QuantifierContext ctx);
}