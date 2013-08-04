package logic.function.reflexive.identity;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.VariableAtom;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;
import logic.function.reflexive.ReflexiveFunctionFactory;

import java.util.Arrays;
import java.util.List;

import static logic.function.factory.validation.GroupValidatorWithNumber.Number.ONE;

/**
 * @author Steven Weston
 */
public class IdentityFunctionFactory<T extends Nameable> extends ReflexiveFunctionFactory<T> {
	public IdentityFunctionFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, T>>> getConstructors() {
		Validator validatorWithoutId = new Validator();
		validatorWithoutId.addValidator(new VariableAtom(), ONE);
		ValidatorAndConstructor<Function<T, T>> constructorWithoutId = new ValidatorAndConstructor<>(
				validatorWithoutId,
				new ConstructorWithoutId<T>()
		);
		return Arrays.asList(constructorWithoutId);
	}

	private static class ConstructorWithoutId<T extends Nameable> implements Constructor<Function<T, T>> {
		@Override
		public Function<T, T> construct(List<ValidationResult> results) {
			StringResult result = (StringResult) results.get(1);
			return new IdentityFunction<>(result.getString());
		}
	}
}
