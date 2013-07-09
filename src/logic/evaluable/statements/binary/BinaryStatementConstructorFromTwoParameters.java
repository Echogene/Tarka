package logic.evaluable.statements.binary;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.factory.FactoryException;
import logic.function.factory.FunctionConstructorFromTwoParameters;

/**
 * @author Steven Weston
 */
public class BinaryStatementConstructorFromTwoParameters<T extends Nameable>
		implements FunctionConstructorFromTwoParameters<
			BinaryStatement<T>,
			Evaluable<T>,
			Evaluable<T>
		> {

	private BinaryConnectiveFactory factory;

	public BinaryStatementConstructorFromTwoParameters(BinaryConnectiveFactory factory) {
		this.factory = factory;
	}

	@Override
	public BinaryStatement<T> construct(Evaluable<T> parameter1, String operator, Evaluable<T> parameter2) throws FactoryException {
		BinaryConnective connective = (BinaryConnective) factory.createElement(operator);
		return new BinaryStatement<>(parameter1, connective, parameter2);
	}
}
