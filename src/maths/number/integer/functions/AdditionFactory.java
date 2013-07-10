package maths.number.integer.functions;

import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.ValidationResult;
import logic.function.factory.binary.BinaryConstructor;
import logic.function.factory.binary.BinaryValidator;
import logic.function.factory.multary.MultaryConstructor;
import logic.function.factory.multary.MultaryValidator;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;
import logic.function.reflexive.identity.IdentityFunctionConstructorFromString;
import maths.number.Number;
import maths.number.Summor;
import reading.lexing.Token;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class AdditionFactory<N extends Number> implements ReflexiveFunctionFactory<N> {
	private final Summor<N> summor;
	private final BinaryValidator binaryValidator;
	private BinaryConstructor<Addition<N>, ReflexiveFunction<N>, ReflexiveFunction<N>> binaryConstructor;
	private MultaryValidator multaryValidator;
	private MultaryConstructor<Addition<N>, ReflexiveFunction<N>> multaryConstructor;

	public AdditionFactory(Summor<N> summor) {
		this.summor = summor;
		this.binaryValidator = new BinaryValidator(Arrays.asList(Addition.PLUS_SYMBOL));
		this.binaryConstructor = new BinaryConstructor<>(
				new AdditionConstructorFromTwoParameters<>(this.summor),
				new IdentityFunctionConstructorFromString<N>(),
				new IdentityFunctionConstructorFromString<N>()
		);
		this.multaryValidator = new MultaryValidator(Arrays.asList(Addition.SUM_SYMBOL), ReflexiveFunction.class);
		this.multaryConstructor = new MultaryConstructor<>(
				new AdditionConstructorFromParameterList<>(this.summor),
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
		}
		result = multaryValidator.validate(tokens, functions);
		if (result.isValid()) {
			return multaryConstructor.construct(result, tokens, functions);
		}
		throw new FactoryException("Could not create Addition");
	}
}
