package logic.function.reflexive.identity;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.ConstructorFromString;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.WordAtom;
import logic.function.factory.validation.group.validators.FunctionOrVariableValidator;
import logic.function.factory.validation.results.FunctionResult;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;

import java.util.Arrays;
import java.util.List;

import static logic.function.factory.validation.GroupValidatorWithNumber.Number.ONE;
import static logic.function.reflexive.identity.IdentityFunction.IDENTITY_NAME;

/**
 * @author Steven Weston
 */
public class IdentityFunctionFactory<T extends Nameable> extends ReflexiveFunctionFactory<T> implements ConstructorFromString<IdentityFunction<T>> {
	public IdentityFunctionFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, T>>> getConstructors() {
		Validator validatorWithoutId = new Validator();
		validatorWithoutId.addValidator(new FunctionOrVariableValidator(ReflexiveFunction.class), ONE);
		ValidatorAndConstructor<Function<T, T>> constructorWithoutId = new ValidatorAndConstructor<>(
				validatorWithoutId,
				new Constructor<T>(1)
		);
		Validator validatorWithId = new Validator();
		validatorWithId.addValidator(new WordAtom(IDENTITY_NAME), ONE);
		validatorWithId.addValidator(new FunctionOrVariableValidator(ReflexiveFunction.class), ONE);
		ValidatorAndConstructor<Function<T, T>> constructorWithId = new ValidatorAndConstructor<>(
				validatorWithId,
				new Constructor<T>(2)
		);
		return Arrays.asList(constructorWithoutId, constructorWithId);
	}

	@Override
	public IdentityFunction<T> construct(String parameterName) {
		return new IdentityFunction<>(parameterName);
	}

	private static class Constructor<T extends Nameable> implements logic.function.factory.construction.Constructor<Function<T, T>> {

		private final int resultIndex;

		private Constructor(int resultIndex) {
			this.resultIndex = resultIndex;
		}

		@Override
		public Function<T, T> construct(List<ValidationResult> results) {
			ValidationResult validationResult = results.get(resultIndex);
			if (validationResult instanceof StringResult) {
				StringResult result = (StringResult) validationResult;
				return new IdentityFunction<>(result.getString());
			} else {
				FunctionResult result = (FunctionResult) validationResult;
				return new IdentityFunction<>((ReflexiveFunction<T>) result.getFunction());
			}
		}
	}
}
