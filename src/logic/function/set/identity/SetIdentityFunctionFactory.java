package logic.function.set.identity;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.ConstructorFromString;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.WordAtom;
import logic.function.factory.validation.group.validators.FunctionOrVariableValidator;
import logic.function.factory.validation.results.FunctionResult;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;
import logic.function.set.SetFunction;
import logic.function.set.SetFunctionFactory;
import logic.set.Set;

import java.util.Arrays;
import java.util.List;

import static logic.function.factory.validation.GroupValidatorWithNumber.Number.ONE;
import static logic.function.set.identity.SetIdentityFunction.SET_IDENTITY_NAME;

/**
 * @author Steven Weston
 */
public class SetIdentityFunctionFactory<T extends Nameable> extends SetFunctionFactory<T> implements ConstructorFromString<SetIdentityFunction<T>> {
	public SetIdentityFunctionFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, Set<T>>>> getConstructors() {
		Validator validatorWithoutId = new Validator();
		validatorWithoutId.addValidator(new FunctionOrVariableValidator(SetFunction.class), ONE);
		ValidatorAndConstructor<Function<T, Set<T>>> constructorWithoutId = new ValidatorAndConstructor<>(
				validatorWithoutId,
				new ConstructorWithoutId<T>()
		);
		Validator validatorWithId = new Validator();
		validatorWithId.addValidator(new WordAtom(SET_IDENTITY_NAME), ONE);
		validatorWithId.addValidator(new FunctionOrVariableValidator(SetFunction.class), ONE);
		ValidatorAndConstructor<Function<T, Set<T>>> constructorWithId = new ValidatorAndConstructor<>(
				validatorWithId,
				new ConstructorWithId<T>()
		);
		return Arrays.asList(constructorWithoutId, constructorWithId);
	}

	@Override
	public SetIdentityFunction<T> construct(String parameterName) {
		return new SetIdentityFunction<>(parameterName);
	}

	private static class ConstructorWithoutId<T extends Nameable> implements Constructor<Function<T, Set<T>>> {
		@Override
		public Function<T, Set<T>> construct(List<ValidationResult> results) {
			ValidationResult validationResult = results.get(1);
			if (validationResult instanceof StringResult) {
				StringResult result = (StringResult) validationResult;
				return new SetIdentityFunction<>(result.getString());
			} else {
				FunctionResult result = (FunctionResult) validationResult;
				return new SetIdentityFunction<>((SetFunction<T>) result.getFunction());
			}
		}
	}

	private static class ConstructorWithId<T extends Nameable> implements Constructor<Function<T, Set<T>>> {
		@Override
		public Function<T, Set<T>> construct(List<ValidationResult> results) {
			ValidationResult validationResult = results.get(2);
			if (validationResult instanceof StringResult) {
				StringResult result = (StringResult) validationResult;
				return new SetIdentityFunction<>(result.getString());
			} else {
				FunctionResult result = (FunctionResult) validationResult;
				return new SetIdentityFunction<>((SetFunction<T>) result.getFunction());
			}
		}
	}
}