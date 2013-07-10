package logic.function.reflexiveset.union;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.*;
import logic.function.reflexiveset.ReflexiveSetFunction;
import logic.function.reflexiveset.ReflexiveSetFunctionFactory;
import logic.function.reflexiveset.identity.SetIdentityFunction;
import logic.function.reflexiveset.identity.SetIdentityFunctionConstructorFromString;
import logic.set.Set;
import reading.lexing.Token;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @author Steven Weston
 */
public class UnionFactory<T extends Nameable> implements ReflexiveSetFunctionFactory<T> {
	private BinaryValidator binaryValidator;
	private BinaryConstructor<Union<T>, ReflexiveSetFunction<T>, ReflexiveSetFunction<T>> binaryConstructor;
	private MultaryValidator multaryValidator;
	private MultaryConstructor<Union<T>, ReflexiveSetFunction<T>> multaryConstructor;

	public UnionFactory() {
		binaryValidator = new BinaryValidator(
				ReflexiveSetFunction.class,
				Collections.singletonList(Union.BINARY_SYMBOL),
				ReflexiveSetFunction.class
		);
		binaryConstructor = new BinaryConstructor<>(
				new UnionConstructorFromTwoParameters<>(),
				new SetIdentityFunctionConstructorFromString<T>(),
				new SetIdentityFunctionConstructorFromString<T>()
		);
		multaryValidator = new MultaryValidator(
				Collections.singletonList(Union.MULTARY_SYMBOL),
				ReflexiveSetFunction.class
		);
		multaryConstructor = new MultaryConstructor<>(
				new UnionConstructorFromParameterList<>(),
				new SetIdentityFunctionConstructorFromString<T>()
		);
	}

	public static <T extends Nameable> Union<T> createElement(String... parameters) {
		java.util.Set<ReflexiveSetFunction<T>> functions = new HashSet<>();
		for (String parameter : parameters) {
			ReflexiveSetFunction<T> function = new SetIdentityFunction<>(parameter);
			functions.add(function);
		}
		return new Union<>(functions);
	}

	@Override
	public Function<Set<T>, Set<T>> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
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

	@Override
	public Function<Set<T>, Set<T>> createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}
}
