package logic.function.reflexiveset.union;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.BinaryConstructor;
import logic.function.factory.BinaryValidator;
import logic.function.factory.MultaryValidator;
import logic.function.factory.ValidationResult;
import logic.function.reflexiveset.ReflexiveSetFunction;
import logic.function.reflexiveset.ReflexiveSetFunctionFactory;
import logic.function.reflexiveset.identity.SetIdentityFunction;
import logic.function.reflexiveset.identity.SetIdentityFunctionConstructorFromString;
import logic.set.Set;
import reading.lexing.Token;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static logic.function.factory.ValidationResult.ValidationType.FUNCTION;
import static logic.function.factory.ValidationResult.ValidationType.TOKEN;

/**
 * @author Steven Weston
 */
public class UnionFactory<T extends Nameable> implements ReflexiveSetFunctionFactory<T> {
	private BinaryValidator binaryValidator;
	private BinaryConstructor<Union<T>, ReflexiveSetFunction<T>, ReflexiveSetFunction<T>> binaryConstructor;
	private MultaryValidator multaryValidator;

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
			return constructMultaryUnion(result, tokens, functions);
		}
		throw new FactoryException("Could not create Union");
	}

	private Union<T> constructMultaryUnion(ValidationResult result, List<Token> tokens, List<Function<?, ?>> functions) {
		int tokenIndex = 1;
		int functionIndex = 0;
		java.util.Set<ReflexiveSetFunction<T>> set = new HashSet<>();
		for (ValidationResult.ValidationType type : result) {
			if (type.equals(TOKEN)) {
				set.add(new SetIdentityFunction<>(tokens.get(tokenIndex++).getValue()));
				functionIndex++;
			} else if (type.equals(FUNCTION)) {
				set.add((ReflexiveSetFunction<T>) functions.get(functionIndex++));
				tokenIndex += 2;
			}
		}
		return new Union<>(set);
	}

	@Override
	public Function<Set<T>, Set<T>> createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}
}
