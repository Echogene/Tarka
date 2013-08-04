package logic.function.reflexive.identity;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.construction.Constructor;
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
public class IdentityFunctionFactory<T extends Nameable> extends ReflexiveFunctionFactory<T> {
	public IdentityFunctionFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, T>>> getConstructors() {
		Validator validatorWithoutId = new Validator();
		validatorWithoutId.addValidator(new FunctionOrVariableValidator(ReflexiveFunction.class), ONE);
		ValidatorAndConstructor<Function<T, T>> constructorWithoutId = new ValidatorAndConstructor<>(
				validatorWithoutId,
				new ConstructorWithoutId<T>()
		);
		Validator validatorWithId = new Validator();
		validatorWithId.addValidator(new WordAtom(IDENTITY_NAME), ONE);
		validatorWithId.addValidator(new FunctionOrVariableValidator(ReflexiveFunction.class), ONE);
		ValidatorAndConstructor<Function<T, T>> constructorWithId = new ValidatorAndConstructor<>(
				validatorWithId,
				new ConstructorWithId<T>()
		);
		return Arrays.asList(constructorWithoutId, constructorWithId);
	}

	private static class ConstructorWithoutId<T extends Nameable> implements Constructor<Function<T, T>> {
		@Override
		public Function<T, T> construct(List<ValidationResult> results) {
			ValidationResult validationResult = results.get(1);
			if (validationResult instanceof StringResult) {
				StringResult result = (StringResult) validationResult;
				return new IdentityFunction<>(result.getString());
			} else {
				FunctionResult result = (FunctionResult) validationResult;
				return new IdentityFunction<>((ReflexiveFunction<T>) result.getFunction());
			}
		}
	}

	private static class ConstructorWithId<T extends Nameable> implements Constructor<Function<T, T>> {
		@Override
		public Function<T, T> construct(List<ValidationResult> results) {
			ValidationResult validationResult = results.get(2);
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
