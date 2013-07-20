package logic.factory;

import logic.evaluable.constants.LogicalConstant;
import logic.evaluable.statements.binary.BinaryConnective;
import logic.evaluable.statements.quantified.Quantifier;
import logic.evaluable.statements.unary.UnaryConnective;
import logic.function.reflexiveset.union.Union;
import maths.number.integer.functions.addition.Addition;
import maths.number.integer.functions.subtraction.Subtraction;
import reading.lexing.Lexer;
import reading.lexing.LexerException;
import reading.lexing.PatternNotRecognisedException;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;

/**
 * @author Steven Weston
 */
public class SimpleLogicLexerImpl implements Lexer {
	private static final String OPERATOR_GROUP =
			"∊∈∉∍∋∌="
			+ BinaryConnective.BINARY_CONNECTIVE_SYMBOLS
			+ UnaryConnective.UNARY_CONNECTIVE_SYMBOLS
			+ LogicalConstant.CONSTANT_SYMBOLS
			+ Union.UNION_SYMBOLS
			+ Addition.ADDITION_SYMBOLS
			+ Subtraction.MINUS;
	private static final String QUANTIFIER_GROUP = Quantifier.QUANTIFIER_SYMBOLS;

	private static final String OPEN_PAREN_REGEX = "\\(";
	private static final Pattern OPEN_PAREN_PATTERN = Pattern.compile(OPEN_PAREN_REGEX);
	private static final String CLOSE_PAREN_REGEX = "\\)";
	private static final Pattern CLOSE_PAREN_PATTERN = Pattern.compile(CLOSE_PAREN_REGEX);
	private static final String NAME_REGEX = "[\\-]?[\\w]+";
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

	@Override
	public List<Token> tokeniseString(String string) throws LexerException {
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
			return new Token(OPEN_PAREN, s);
		} else if (CLOSE_PAREN_PATTERN.matcher(s).matches()) {
			return new Token(CLOSE_PAREN, s);
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
