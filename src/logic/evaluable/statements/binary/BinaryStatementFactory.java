package logic.evaluable.statements.binary;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.evaluable.EvaluableFactory;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.BinaryValidator;
import logic.function.factory.ValidationResult;
import reading.lexing.Token;

import java.util.List;

import static logic.function.factory.ValidationResult.ValidationType.FUNCTION;

/**
 * @author Steven Weston
 */
public class BinaryStatementFactory<T extends Nameable> implements EvaluableFactory<T> {
	private BinaryConnectiveFactory connectiveFactory;
	private BinaryValidator validator;

	public BinaryStatementFactory() {
		connectiveFactory = new BinaryConnectiveFactory();
		validator = new BinaryValidator(Evaluable.class, BinaryConnective.BINARY_CONNECTIVE_SYMBOL_LIST, Evaluable.class);
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		ValidationResult result = validator.validate(tokens, functions);
		if (result.isValid() && result.get(0).equals(FUNCTION) && result.get(1).equals(FUNCTION)) {
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
}
