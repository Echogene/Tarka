package logic.function.evaluable.statements.binary;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.EvaluableFactory;
import logic.function.evaluable.constants.LogicalConstantFactory;
import logic.function.factory.binary.BinaryValidator;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.FunctionConvertor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;

import java.util.Arrays;
import java.util.List;

import static logic.function.evaluable.statements.binary.BinaryConnective.BINARY_CONNECTIVE_SYMBOL_LIST;

/**
 * @author Steven Weston
 */
public class BinaryStatementFactory<T extends Nameable> extends EvaluableFactory<T, BinaryStatement<T>> {

	public BinaryStatementFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<BinaryStatement<T>>> getConstructors() {
		Validator validator = new BinaryValidator(Evaluable.class, BINARY_CONNECTIVE_SYMBOL_LIST, Evaluable.class);
		return Arrays.asList(
				new ValidatorAndConstructor<>(
						validator,
						new BinaryStatementConstructor<T>()
				)
		);
	}

	private static class BinaryStatementConstructor<T extends Nameable> implements Constructor<BinaryStatement<T>> {

		private final FunctionConvertor<Evaluable<T>, T> constructor;

		private BinaryStatementConstructor() {
			this.constructor = new FunctionConvertor<Evaluable<T>, T>(new LogicalConstantFactory<T>());
		}

		@Override
		public BinaryStatement<T> construct(List<ValidationResult> results) throws FactoryException {
			Evaluable<T> firstFunction = constructor.convert(results.get(1));

			StringResult connectiveResult = (StringResult) results.get(2);
			BinaryConnective binaryConnective = BinaryConnectiveFactory.create(connectiveResult.getString());

			Evaluable<T> secondFunction = constructor.convert(results.get(3));
			return new BinaryStatement<>(firstFunction, binaryConnective, secondFunction);
		}
	}
}
