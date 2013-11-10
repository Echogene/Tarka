package logic.function.set.identity;

import logic.Nameable;
import logic.function.factory.ConstructorFromString;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.oldvalidation.SimpleLogicValidator;
import logic.function.factory.oldvalidation.WordAtom;
import logic.function.factory.oldvalidation.group.validators.FunctionOrVariableValidator;
import logic.function.factory.oldvalidation.results.FunctionResult;
import logic.function.factory.oldvalidation.results.StringResult;
import logic.function.factory.oldvalidation.results.ValidationResult;
import logic.function.set.SetFunction;
import logic.function.set.SetFunctionFactory;

import java.util.Arrays;
import java.util.List;

import static logic.function.factory.oldvalidation.GroupValidatorWithNumber.Number.ONE;
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
		SimpleLogicValidator validatorWithoutId = new SimpleLogicValidator();
		validatorWithoutId.addValidator(ONE, new FunctionOrVariableValidator(SetFunction.class));
		ValidatorAndConstructor<SetIdentityFunction<T>> constructorWithoutId = new ValidatorAndConstructor<>(
				validatorWithoutId,
				new Constructor<>(1)
		);
		SimpleLogicValidator validatorWithId = new SimpleLogicValidator();
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