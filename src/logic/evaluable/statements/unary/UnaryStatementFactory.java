package logic.evaluable.statements.unary;

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
public class UnaryStatementFactory<T extends Nameable> extends EvaluableFactory<T> {
	UnaryConnectiveFactory connectiveFactory;

	public UnaryStatementFactory() {
		connectiveFactory = new UnaryConnectiveFactory();
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		if (matchesFunction(functions)) {
			if (matchesTwoBrackets(tokens)) {
				return new UnaryStatement<>((Evaluable<T>) functions.get(0));
			} else if (matchesStandardTokens(tokens)) {
				return new UnaryStatement<>(
						connectiveFactory.createElement(tokens.get(0).getValue()),
						(Evaluable<T>) functions.get(0));
			}
		}
		throw new FactoryException("Could not create UnaryStatement");
	}

	public boolean matchesTwoBrackets(List<Token> tokens) {
		return tokens != null
				&& tokens.size() == 2
				&& tokens.get(0).isOfType(OPEN_PAREN)
				&& tokens.get(1).isOfType(CLOSE_PAREN);
	}

	public boolean matchesStandardTokens(List<Token> tokens) {
		return tokens != null
				&& tokens.size() == 3
				&& tokens.get(0).isOfType(OPERATOR)
				&& UnaryConnective.UNARY_CONNECTIVE_SYMBOL_LIST.contains(tokens.get(0).getValue())
				&& tokens.get(1).isOfType(OPEN_PAREN)
				&& tokens.get(2).isOfType(CLOSE_PAREN);
	}

	public boolean matchesFunction(List<Function<?, ?>> functions) {
		return functions != null
				&& functions.size() == 1
				&& functions.get(0) != null
				&& functions.get(0) instanceof Evaluable<?>;
	}
}
