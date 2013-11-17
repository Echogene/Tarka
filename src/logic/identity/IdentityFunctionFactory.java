package logic.identity;

import logic.Nameable;
import logic.function.factory.ConstructorFromString;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.oldvalidation.SimpleLogicValidator;
import logic.function.factory.oldvalidation.WordAtom;
import logic.function.factory.oldvalidation.group.validators.FunctionOrVariableValidator;
import logic.function.factory.oldvalidation.results.FunctionResult;
import logic.function.factory.oldvalidation.results.StringResult;
import logic.function.factory.oldvalidation.results.ValidationResult;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;

import java.util.Arrays;
import java.util.List;

import static logic.function.factory.oldvalidation.GroupValidatorWithNumber.Number.ONE;
import static logic.identity.IdentityFunction.IDENTITY_NAME;

/**
 * @author Steven Weston
 */
public class IdentityFunctionFactory<T extends Nameable>
		extends ReflexiveFunctionFactory<T, IdentityFunction<T>>
		implements ConstructorFromString<IdentityFunction<T>> {

	public IdentityFunctionFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<IdentityFunction<T>>> getConstructors() {
		SimpleLogicValidator validatorWithoutId = new SimpleLogicValidator();
		validatorWithoutId.addValidator(ONE, new FunctionOrVariableValidator(ReflexiveFunction.class));
		ValidatorAndConstructor<IdentityFunction<T>> constructorWithoutId = new ValidatorAndConstructor<>(
				validatorWithoutId,
				new IdentityFunctionConstructor<>(1)
		);
		SimpleLogicValidator validatorWithId = new SimpleLogicValidator();
		validatorWithId.addValidator(ONE, new WordAtom(IDENTITY_NAME));
		validatorWithId.addValidator(ONE, new FunctionOrVariableValidator(ReflexiveFunction.class));
		ValidatorAndConstructor<IdentityFunction<T>> constructorWithId = new ValidatorAndConstructor<>(
				validatorWithId,
				new IdentityFunctionConstructor<>(2)
		);
		return Arrays.asList(constructorWithoutId, constructorWithId);
	}

	@Override
	public IdentityFunction<T> construct(String parameterName) {
		return new IdentityFunction<>(parameterName);
	}

	private static class IdentityFunctionConstructor<T extends Nameable> implements Constructor<IdentityFunction<T>> {

		private final int resultIndex;

		private IdentityFunctionConstructor(int resultIndex) {
			this.resultIndex = resultIndex;
		}

		@Override
		public IdentityFunction<T> construct(List<ValidationResult> results) {
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
