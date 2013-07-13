package logic.evaluable.constants;

import logic.Nameable;
import logic.evaluable.EvaluableFactory;
import logic.factory.FactoryException;
import logic.function.Function;
import reading.lexing.Token;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPERATOR;

/**
 * A {@code Factory} for creating {@code LogicalConstant}s.
 * @author Steven Weston
 */
public class LogicalConstantFactory<T extends Nameable> extends EvaluableFactory<T> {
	@Override
	public Function<T, Boolean> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		if ((functions == null || functions.size() == 0) && matches(tokens)) {
			return new LogicalConstant<>(tokens.get(0).getValue().equals(LogicalConstant.TAUTOLOGY_SYMBOL));
		}
		throw new FactoryException("Could not create LogicalConstant");
	}

	/**
	 * Returns whether the {@code Token}s in the {@code List} match the format of a {@code LogicalConstant}.  This
	 * format is just one token that matches either a contradiction or a tautology.
	 * @param tokens A {@code List} of {@code Token}s to match.
	 * @return {@code true} when the {@code Token}s match the format.
	 */
	boolean matches(List<Token> tokens) {
		return tokens != null
				&& tokens.size() == 1
				&& tokens.get(0).isOfType(OPERATOR)
				&& LogicalConstant.CONSTANT_SYMBOL_LIST.contains(tokens.get(0).getValue());
	}
}
