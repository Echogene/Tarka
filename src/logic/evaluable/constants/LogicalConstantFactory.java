package logic.evaluable.constants;

import logic.Nameable;
import logic.evaluable.EvaluableFactory;
import logic.function.Function;
import logic.function.factory.ConstructorFromString;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.group.validators.OperatorAtom;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;

import java.util.Arrays;
import java.util.List;

import static logic.evaluable.constants.LogicalConstant.CONSTANT_SYMBOL_LIST;
import static logic.evaluable.constants.LogicalConstant.TAUTOLOGY_SYMBOL;
import static logic.function.factory.validation.GroupValidatorWithNumber.Number.ONE;

/**
 * A {@code Factory} for creating {@code LogicalConstant}s.
 * @author Steven Weston
 */
public class LogicalConstantFactory<T extends Nameable> extends EvaluableFactory<T> implements ConstructorFromString<LogicalConstant<T>> {

	public LogicalConstantFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, Boolean>>> getConstructors() {
		Validator validator = new Validator();
		validator.addValidator(new OperatorAtom(CONSTANT_SYMBOL_LIST), ONE);
		return Arrays.asList(
				new ValidatorAndConstructor<>(
						validator,
						new LogicalConstantConstructor<T>()
				)
		);
	}

	@Override
	public LogicalConstant<T> construct(String parameterName) {
		return new LogicalConstant<>(TAUTOLOGY_SYMBOL.equals(parameterName));
	}

	private static class LogicalConstantConstructor<T extends Nameable> implements Constructor<Function<T, Boolean>> {
		@Override
		public Function<T, Boolean> construct(List<ValidationResult> results) {
			StringResult result = (StringResult) results.get(1);
			LogicalConstantFactory factory = new LogicalConstantFactory();
			return factory.construct(result.getString());
		}
	}
}
