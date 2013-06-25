package logic.evaluable.statements.binary;

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
public class BinaryStatementFactory<T extends Nameable> implements EvaluableFactory<T> {
	private BinaryConnectiveFactory connectiveFactory;

	public BinaryStatementFactory() {
		connectiveFactory = new BinaryConnectiveFactory();
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		if (matchesTokens(tokens) && matchesFunctions(functions)) {
			return new BinaryStatement<>(
					(Evaluable<T>) functions.get(0),
					(BinaryConnective) connectiveFactory.createElement(tokens.get(2).getValue()),
					(Evaluable<T>) functions.get(1));
		}
		throw new FactoryException("Could not create BinaryStatement");
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens) throws FactoryException {
		throw new FactoryException("Could not create BinaryStatement with no functions");
	}

	public boolean matchesTokens(List<Token> tokens) {
		return tokens.size() == 5
				&& tokens.get(0).isOfType(OPEN_PAREN)
				&& tokens.get(1).isOfType(CLOSE_PAREN)
				&& tokens.get(2).isOfType(OPERATOR)
				&& BinaryConnective.BINARY_CONNECTIVE_SYMBOL_LIST.contains(tokens.get(2).getValue())
				&& tokens.get(3).isOfType(OPEN_PAREN)
				&& tokens.get(4).isOfType(CLOSE_PAREN);
	}

	public boolean matchesFunctions(List<Function<?, ?>> functions) {
		return functions != null
				&& functions.size() == 2
				&& functions.get(0) != null
				&& functions.get(0) instanceof Evaluable<?>
				&& functions.get(1) != null
				&& functions.get(1) instanceof Evaluable<?>;
	}
}
