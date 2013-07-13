package logic.evaluable.statements.binary;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.evaluable.EvaluableFactory;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.ValidationResult;
import logic.function.factory.binary.BinaryConstructor;
import logic.function.factory.binary.BinaryValidator;
import reading.lexing.Token;

import java.util.List;

/**
 * @author Steven Weston
 */
public class BinaryStatementFactory<T extends Nameable> extends EvaluableFactory<T> {
	private BinaryConnectiveFactory connectiveFactory;
	private BinaryValidator validator;
	private BinaryConstructor<BinaryStatement<T>, Evaluable<T>, Evaluable<T>> constructor;

	public BinaryStatementFactory() {
		connectiveFactory = new BinaryConnectiveFactory();
		validator = new BinaryValidator(Evaluable.class, BinaryConnective.BINARY_CONNECTIVE_SYMBOL_LIST, Evaluable.class);
		this.constructor = new BinaryConstructor<>(new BinaryStatementConstructorFromTwoParameters<>(new BinaryConnectiveFactory()), null, null);
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		ValidationResult result = validator.validate(tokens, functions);
		if (result.isValid()) {
			return constructor.construct(result, tokens, functions);
		}
		throw new FactoryException("Could not create BinaryStatement");
	}
}
