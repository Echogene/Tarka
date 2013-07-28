package logic.function.set.union;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.ValidationResult;
import logic.function.factory.binary.BinaryConstructor;
import logic.function.factory.binary.BinaryValidator;
import logic.function.factory.multary.MultaryConstructor;
import logic.function.factory.multary.MultaryValidator;
import logic.function.set.SetFunction;
import logic.function.set.SetFunctionFactory;
import logic.function.set.identity.SetIdentityFunction;
import logic.function.set.identity.SetIdentityFunctionConstructorFromString;
import logic.set.Set;
import reading.lexing.Token;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @author Steven Weston
 */
public class UnionFactory<T extends Nameable> extends SetFunctionFactory<T> {
	private BinaryValidator binaryValidator;
	private BinaryConstructor<Union<T>, SetFunction<T>, SetFunction<T>> binaryConstructor;
	private MultaryValidator multaryValidator;
	private MultaryConstructor<Union<T>, SetFunction<T>> multaryConstructor;

	public UnionFactory() {
		binaryValidator = new BinaryValidator(
				SetFunction.class,
				Collections.singletonList(Union.BINARY_SYMBOL),
				SetFunction.class
		);
		binaryConstructor = new BinaryConstructor<>(
				new UnionConstructorFromTwoParameters<>(),
				new SetIdentityFunctionConstructorFromString<T>(),
				new SetIdentityFunctionConstructorFromString<T>()
		);
		multaryValidator = new MultaryValidator(
				Collections.singletonList(Union.MULTARY_SYMBOL),
				SetFunction.class
		);
		multaryConstructor = new MultaryConstructor<>(
				new UnionConstructorFromParameterList<>(),
				new SetIdentityFunctionConstructorFromString<T>()
		);
	}

	public static <T extends Nameable> Union<T> createElement(String... parameters) {
		java.util.Set<SetFunction<T>> functions = new HashSet<>();
		for (String parameter : parameters) {
			SetFunction<T> function = new SetIdentityFunction<>(parameter);
			functions.add(function);
		}
		return new Union<>(functions);
	}

	@Override
	public Function<T, Set<T>> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		tokens = validateAndStripParentheses(tokens);
		ValidationResult result = binaryValidator.validate(tokens, functions);
		if (result.isValid()) {
			return binaryConstructor.construct(result, tokens, functions);
		}
		result = multaryValidator.validate(tokens, functions);
		if (result.isValid()) {
			return multaryConstructor.construct(result, tokens, functions);
		}
		throw new FactoryException("Could not create Union");
	}
}
