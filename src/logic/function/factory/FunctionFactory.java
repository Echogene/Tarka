package logic.function.factory;

import logic.Nameable;
import logic.factory.Factory;
import logic.factory.FactoryException;
import logic.function.Function;
import reading.lexing.Token;
import util.CollectionUtils;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.isTokenCloseParenthesis;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.isTokenOpenParenthesis;

/**
 * @author Steven Weston
 */
public abstract class FunctionFactory<D extends Nameable, C> implements Factory<Function<D, C>> {

	@Override
	public Function<D, C> createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}

	public abstract Function<D, C> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException;

	protected List<Token> validateAndStripParentheses(List<Token> tokens) throws FactoryException {
		if (tokens.size() < 2) {
			throw new FactoryException("There were fewer than two tokens.");
		} else if (!isTokenOpenParenthesis(tokens.get(0))
				|| !isTokenCloseParenthesis(tokens.get(tokens.size() - 1))) {
			throw new FactoryException("The outer brackets were not parentheses.");
		}
		return CollectionUtils.stripFirstAndLast(tokens);
	}
}
