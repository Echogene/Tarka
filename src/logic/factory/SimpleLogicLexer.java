package logic.factory;

import logic.function.evaluable.statements.binary.BinaryConnective;
import logic.function.evaluable.statements.quantified.standard.Quantifier;
import logic.function.evaluable.statements.unary.UnaryConnective;
import logic.function.maths.number.addition.Addition;
import logic.function.maths.number.multiplication.Multiplication;
import logic.function.maths.number.subtraction.Subtraction;
import logic.function.set.complex.ComplexSet;
import logic.function.set.intersection.Intersection;
import logic.function.set.union.Union;
import logic.function.voidfunction.definition.constant.MemberDefinition;
import logic.function.voidfunction.definition.function.FunctionDefinition;
import org.jetbrains.annotations.NotNull;
import reading.lexing.Lexer;
import reading.lexing.LexerException;
import reading.lexing.PatternNotRecognisedException;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;

/**
 * @author Steven Weston
 */
public class SimpleLogicLexer implements Lexer {
	private static final String OPERATOR_GROUP =
			"∊∈∉∍∋∌=/\\"
			+ BinaryConnective.BINARY_CONNECTIVE_SYMBOLS
			+ UnaryConnective.UNARY_CONNECTIVE_SYMBOLS
			+ Union.UNION_SYMBOLS
			+ Intersection.INTERSECTION_SYMBOLS
			+ Addition.ADDITION_SYMBOLS
			+ Multiplication.MULTIPLICATION_SYMBOLS
			+ Subtraction.MINUS
			+ MemberDefinition.DEFINITION_SYMBOL
			+ FunctionDefinition.DEFINITION_SYMBOL
			+ ComplexSet.SUCH_THAT;
	private static final String QUANTIFIER_GROUP = Quantifier.QUANTIFIER_SYMBOLS;

	private static final String OPEN_PAREN_REGEX = "[\\(\\{\\[]";
	private static final Pattern OPEN_PAREN_PATTERN = Pattern.compile(OPEN_PAREN_REGEX);
	private static final String CLOSE_PAREN_REGEX = "[\\)\\}\\]]";
	private static final Pattern CLOSE_PAREN_PATTERN = Pattern.compile(CLOSE_PAREN_REGEX);
	private static final String NAME_REGEX = "[\\-]?[\\wℤℙ⊤⊥∅]+";
	private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
	private static final String OPERATOR_REGEX = "[" + OPERATOR_GROUP + "]";
	private static final Pattern OPERATOR_PATTERN = Pattern.compile(OPERATOR_REGEX);
	private static final String QUANTIFIER_REGEX = "[" + QUANTIFIER_GROUP + "]+";
	private static final Pattern QUANTIFIER_PATTERN = Pattern.compile(QUANTIFIER_REGEX);

	private static final String PAREN_SPLIT_REGEX = "(?<=" + OPEN_PAREN_REGEX + ")|(?=" + CLOSE_PAREN_REGEX
			+ ")|(?<=\\S)(?=" + OPEN_PAREN_REGEX + ")";
	private static final String OPERATOR_SPLIT_REGEX = "(?<=[" + OPERATOR_GROUP + "])(?=[^" + QUANTIFIER_GROUP + "])"
			+ "|(?<=\\S)(?=[" + OPERATOR_GROUP + "][^" + QUANTIFIER_GROUP + "])";
	private static final String QUANTIFIER_SPLIT_REGEX = "(?<=[" + QUANTIFIER_GROUP + "]{2})"
			+ "|(?<=[" + QUANTIFIER_GROUP + "])(?=[^" + QUANTIFIER_GROUP + "])";

	@NotNull
	@Override
	public List<Token> tokeniseString(@NotNull String string) throws LexerException {
		if (string.isEmpty()) {
			return emptyList();
		}
		ArrayList<Token> output = new ArrayList<>();
		String[] splitArray = string.split(
				"(\\s)"
				+ "|" + PAREN_SPLIT_REGEX
				+ "|" + OPERATOR_SPLIT_REGEX
				+ "|" + QUANTIFIER_SPLIT_REGEX);
		for (String s : splitArray) {
			output.add(lexAtom(s));
		}
		return output;
	}

	public static Token lexAtom(String s) throws LexerException {
		// todo: this might be able to be neater
		if (OPEN_PAREN_PATTERN.matcher(s).matches()) {
			return new Token(OPEN_BRACKET, s);
		} else if (CLOSE_PAREN_PATTERN.matcher(s).matches()) {
			return new Token(CLOSE_BRACKET, s);
		} else if (NAME_PATTERN.matcher(s).matches()) {
			return new Token(NAME, s);
		} else if (OPERATOR_PATTERN.matcher(s).matches()) {
			return new Token(OPERATOR, s);
		} else if (QUANTIFIER_PATTERN.matcher(s).matches()) {
			return new Token(QUANTIFIER, s);
		} else {
			throw new PatternNotRecognisedException(s + " was not recognised");
		}
	}
}
