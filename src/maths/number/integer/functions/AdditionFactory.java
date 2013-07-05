package maths.number.integer.functions;

import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.BinaryConstructor;
import logic.function.factory.BinaryValidator;
import logic.function.factory.ValidationResult;
import logic.function.reflexive.IdentityFunctionConstructorFromString;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;
import maths.number.Number;
import maths.number.Summor;
import reading.lexing.Token;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class AdditionFactory<N extends Number> implements ReflexiveFunctionFactory<N> {
	private final BinaryValidator binaryValidator;
	private BinaryConstructor<Addition<N>, ReflexiveFunction<N>, ReflexiveFunction<N>> binaryConstructor;
	private final Summor<N> summor;

	public AdditionFactory(Summor<N> summor) {
		this.binaryValidator = new BinaryValidator(Arrays.asList(Addition.PLUS_SYMBOL));
		this.summor = summor;
		this.binaryConstructor = new BinaryConstructor<>(
				new AdditionConstructorFromTwoParameters<>(this.summor),
				new IdentityFunctionConstructorFromString<N>(),
				new IdentityFunctionConstructorFromString<N>()
		);
	}

	@Override
	public Function<N, N> createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}

	@Override
	public Function<N, N> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		ValidationResult result = binaryValidator.validate(tokens, functions);
		if (result.isValid()) {
			return binaryConstructor.construct(result, tokens, functions);
		} else {
			// todo: multary validator stuff
		}

		throw new FactoryException("Could not create Addition");
	}
}
