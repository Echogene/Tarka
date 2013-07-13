package logic.evaluable.statements.quantified;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.evaluable.EvaluableFactory;
import logic.factory.FactoryException;
import logic.function.Function;
import reading.lexing.Token;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;

/**
 * @author Steven Weston
 */
public class QuantifiedStatementFactory<T extends Nameable> extends EvaluableFactory<T> {
	QuantifierFactory quantifierFactory;

	public QuantifiedStatementFactory() {
		quantifierFactory = new QuantifierFactory();
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens, List<Function<?, ?>> functions)
			throws FactoryException {

		if (matchesTokens(tokens) && matchesFunctions(functions)) {
			if(functions.size() == 2) {
				functions = functions.subList(1, 2);
			}
			return new QuantifiedStatement<>(
					(Quantifier) quantifierFactory.createElement(tokens.get(0).getValue()),
					tokens.get(1).getValue(),
					(Evaluable<T>) functions.get(0)
			);
		}
		throw new FactoryException("Could not create QuantifiedStatement");
	}

	public boolean matchesTokens(List<Token> tokens) {
		return tokens != null
				&& tokens.size() == 4
				&& tokens.get(0).isOfType(QUANTIFIER)
				&& tokens.get(1).isOfType(NAME)
				&& tokens.get(2).isOfType(OPEN_PAREN)
				&& tokens.get(3).isOfType(CLOSE_PAREN);
	}

	public boolean matchesFunctions(List<Function<?, ?>> tokens) {
		return tokens != null
				&& (
					(tokens.size() == 1
						&& tokens.get(0) != null
						&& tokens.get(0) instanceof Evaluable<?>)
					|| (tokens.size() == 2
						&& tokens.get(0) == null
						&& tokens.get(1) != null
						&& tokens.get(1) instanceof Evaluable<?>)
				);
	}
}
