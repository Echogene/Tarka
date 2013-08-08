package logic.evaluable.statements.binary;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.evaluable.EvaluableFactory;
import logic.evaluable.constants.LogicalConstantFactory;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.binary.BinaryValidator;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.results.FunctionResult;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;

import java.util.Arrays;
import java.util.List;

import static logic.evaluable.statements.binary.BinaryConnective.BINARY_CONNECTIVE_SYMBOL_LIST;

/**
 * @author Steven Weston
 */
public class BinaryStatementFactory<T extends Nameable> extends EvaluableFactory<T> {

	public BinaryStatementFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, Boolean>>> getConstructors() {
		Validator validator = new BinaryValidator(Evaluable.class, BINARY_CONNECTIVE_SYMBOL_LIST, Evaluable.class);
		return Arrays.asList(
				new ValidatorAndConstructor<>(
						validator,
						new BinaryStatementConstructor<T>()
				)
		);
	}

	private static class BinaryStatementConstructor<T extends Nameable> implements Constructor<Function<T, Boolean>> {
		@Override
		public Function<T, Boolean> construct(List<ValidationResult> results) throws FactoryException {
			Evaluable<T> firstFunction;
			Evaluable<T> secondFunction;
			ValidationResult firstResult = results.get(1);
			if (firstResult instanceof StringResult) {
				firstFunction = LogicalConstantFactory.create(((StringResult) firstResult).getString());
			} else {
				firstFunction = (Evaluable<T>) ((FunctionResult) firstResult).getFunction();
			}

			StringResult connectiveResult = (StringResult) results.get(2);
			BinaryConnective binaryConnective = BinaryConnectiveFactory.create(connectiveResult.getString());

			ValidationResult secondResult = results.get(3);
			if (secondResult instanceof StringResult) {
				secondFunction = LogicalConstantFactory.create(((StringResult) secondResult).getString());
			} else {
				secondFunction = (Evaluable<T>) ((FunctionResult) secondResult).getFunction();
			}
			return new BinaryStatement<T>(firstFunction, binaryConnective, secondFunction);
		}
	}
}
