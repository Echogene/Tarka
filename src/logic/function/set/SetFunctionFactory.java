package logic.function.set;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.set.Set;
import reading.lexing.Token;
import util.CollectionUtils;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.isTokenCloseCurlyBracket;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.isTokenOpenCurlyBracket;

/**
 * @author Steven Weston
 */
public abstract class SetFunctionFactory<T extends Nameable> extends FunctionFactory<T, Set<T>> {

	public SetFunctionFactory(List<ValidatorAndConstructor<Function<T, Set<T>>>> constructors) {
		super(constructors);
	}

	protected List<Token> validateAndStripCurlyBrackets(List<Token> tokens) throws FactoryException {
		if (tokens.size() < 2) {
			throw new FactoryException("There were fewer than two tokens.");
		} else if (!isTokenOpenCurlyBracket(tokens.get(0))
				|| !isTokenCloseCurlyBracket(tokens.get(tokens.size() - 1))) {
			throw new FactoryException("The outer brackets were not curly brackets.");
		}
		return CollectionUtils.stripFirstAndLast(tokens);
	}
}
