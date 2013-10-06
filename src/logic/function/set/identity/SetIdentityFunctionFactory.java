package logic.function.set.identity;

import logic.Nameable;
import logic.function.factory.ConstructorFromString;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.WordAtom;
import logic.function.factory.validation.group.validators.FunctionOrVariableValidator;
import logic.function.factory.validation.results.FunctionResult;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;
import logic.function.set.SetFunction;
import logic.function.set.SetFunctionFactory;

import java.util.Arrays;
import java.util.List;

import static logic.function.factory.validation.GroupValidatorWithNumber.Number.ONE;
import static logic.function.set.identity.SetIdentityFunction.SET_IDENTITY_NAME;

/**
 * @author Steven Weston
 */
public class SetIdentityFunctionFactory<T extends Nameable>
		extends SetFunctionFactory<T, SetIdentityFunction<T>>
		implements ConstructorFromString<SetIdentityFunction<T>> {

	public SetIdentityFunctionFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<SetIdentityFunction<T>>> getConstructors() {
		Validator validatorWithoutId = new Validator();
		validatorWithoutId.addValidator(ONE, new FunctionOrVariableValidator(SetFunction.class));
		ValidatorAndConstructor<SetIdentityFunction<T>> constructorWithoutId = new ValidatorAndConstructor<>(
				validatorWithoutId,
				new Constructor<>(1)
		);
		Validator validatorWithId = new Validator();
		validatorWithId.addValidator(ONE, new WordAtom(SET_IDENTITY_NAME));
		validatorWithId.addValidator(ONE, new FunctionOrVariableValidator(SetFunction.class));
		ValidatorAndConstructor<SetIdentityFunction<T>> constructorWithId = new ValidatorAndConstructor<>(
				validatorWithId,
				new Constructor<>(2)
		);
		return Arrays.asList(constructorWithoutId, constructorWithId);
	}

	@Override
	public SetIdentityFunction<T> construct(String parameterName) {
		return new SetIdentityFunction<>(parameterName);
	}

	private static class Constructor<T extends Nameable> implements logic.function.factory.construction.Constructor<SetIdentityFunction<T>> {

		private final int resultIndex;

		public Constructor(int resultIndex) {
			this.resultIndex = resultIndex;
		}

		@Override
		public SetIdentityFunction<T> construct(List<ValidationResult> results) {
			ValidationResult validationResult = results.get(resultIndex);
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