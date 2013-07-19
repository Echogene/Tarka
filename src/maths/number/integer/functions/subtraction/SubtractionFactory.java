package maths.number.integer.functions.subtraction;

import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.ValidationResult;
import logic.function.factory.binary.BinaryConstructor;
import logic.function.factory.binary.BinaryValidator;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;
import logic.function.reflexive.identity.IdentityFunctionConstructorFromString;
import maths.number.Number;
import maths.number.Subtractor;
import reading.lexing.Token;

import java.util.List;

/**
 * @author Steven Weston
 */
public class SubtractionFactory<N extends Number> extends ReflexiveFunctionFactory<N> {
	private final Subtractor<N> subtractor;

	private BinaryValidator validator;
	private BinaryConstructor<Subtraction<N>, ReflexiveFunction<N>, ReflexiveFunction<N>> constructor;

	public SubtractionFactory(Subtractor<N> subtractor) {
		this.subtractor = subtractor;

		this.validator = new BinaryValidator(Subtraction.SYMBOL_LIST);
		this.constructor = new BinaryConstructor<>(
				new SubtractionConstructorFromTwoParameters<>(subtractor),
				new IdentityFunctionConstructorFromString<N>(),
				new IdentityFunctionConstructorFromString<N>()
		);
	}

	@Override
	public Function<N, N> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		ValidationResult result = validator.validate(tokens, functions);
		if (result.isValid()) {
			return constructor.construct(result, tokens, functions);
		}
		throw new FactoryException("Could not create Subtraction");
	}
}
