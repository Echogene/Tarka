package logic.evaluable.statements.binary;

import logic.Nameable;
import logic.evaluable.Evaluable;
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

	private BinaryConnective connective;

	public BinaryStatementConstructorFromTwoParameters(BinaryConnective connective) {
		this.connective = connective;
	}

	@Override
	public BinaryStatement<T> construct(Evaluable<T> parameter1, Evaluable<T> parameter2) {
		return new BinaryStatement<>(parameter1, connective, parameter2);
	}
}
