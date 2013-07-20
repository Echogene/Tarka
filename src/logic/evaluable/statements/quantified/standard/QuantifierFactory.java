package logic.evaluable.statements.quantified.standard;

import logic.ConnectiveFactory;
import logic.factory.FactoryException;
import reading.lexing.Token;

import java.util.List;

import static logic.evaluable.statements.quantified.standard.Quantifier.*;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.QUANTIFIER;

/**
 * @author Steven Weston
 */
public class QuantifierFactory implements ConnectiveFactory<Quantifier> {
	@Override
	public Quantifier createElement(String string) throws FactoryException {
		switch (string) {
			case EXISTS_SYMBOL:
				return new Quantifier(QuantifierType.EXISTS);
			case NEXISTS_SYMBOL:
				return new Quantifier(QuantifierType.NEXISTS);
			case FOR_ALL_SYMBOL:
				return new Quantifier(QuantifierType.FOR_ALL);
			case NFOR_ALL_SYMBOL:
				return new Quantifier(QuantifierType.NFOR_ALL);
			case EXISTS_UNIQUE_SYMBOL:
				return new Quantifier(QuantifierType.EXISTS_UNIQUE);
			case NEXISTS_UNIQUE_SYMBOL:
				return new Quantifier(QuantifierType.NEXISTS_UNIQUE);
			default:
				throw new FactoryException("Could not create Quantifier");
		}
	}

	@Override
	public Quantifier createElement(List<Token> tokens) throws FactoryException {
		if (matchesOneQuantifierToken(tokens)) {
			return createElement(tokens.get(0).getValue());
		}
		throw new FactoryException("Could not create Quantifier");
	}

	private boolean matchesOneQuantifierToken(List<Token> tokens) {
		return tokens != null
				&& tokens.size() == 1
				&& tokens.get(0).isOfType(QUANTIFIER);
	}
}
