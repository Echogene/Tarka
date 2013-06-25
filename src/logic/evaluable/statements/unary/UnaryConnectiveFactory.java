package logic.evaluable.statements.unary;

import logic.Connective;
import logic.ConnectiveFactory;
import logic.factory.FactoryException;
import reading.lexing.Token;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPERATOR;

/**
 * @author Steven Weston
 */
public class UnaryConnectiveFactory implements ConnectiveFactory {
	@Override
	public Connective createElement(List<Token> tokens) throws FactoryException {
		if (matchesTokens(tokens)) {
			if (tokens.get(0).getValue().equals(UnaryConnective.NEGATION_SYMBOL)) {
				return new UnaryConnective(UnaryConnective.UnaryConnectiveType.NEGATION);
			}
		}
		throw new FactoryException("Could not create UnaryConnective");
	}

	@Override
	public Connective createElement(String string) throws FactoryException {
		if (string.equals(UnaryConnective.NEGATION_SYMBOL)) {
			return new UnaryConnective(UnaryConnective.UnaryConnectiveType.NEGATION);
		} else if (string.equals(UnaryConnective.EMPTY_SYMBOL)) {
			return new UnaryConnective(UnaryConnective.UnaryConnectiveType.EMPTY);
		}
		throw new FactoryException("Could not create UnaryConnective");
	}

	public boolean matchesTokens(List<Token> tokens) {
		return tokens != null
				&& tokens.size() == 1
				&& tokens.get(0).isOfType(OPERATOR)
				&& UnaryConnective.UNARY_CONNECTIVE_SYMBOL_LIST.contains(tokens.get(0).getValue());
	}
}
