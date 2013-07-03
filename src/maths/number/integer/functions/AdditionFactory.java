package maths.number.integer.functions;

import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.BinaryValidator;
import logic.function.factory.ValidationResult;
import logic.function.reflexive.IdentityFunction;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;
import maths.number.Number;
import maths.number.Summor;
import reading.lexing.Token;

import java.util.Arrays;
import java.util.List;

import static logic.function.factory.ValidationResult.ValidationType.FUNCTION;
import static logic.function.factory.ValidationResult.ValidationType.TOKEN;

/**
 * @author Steven Weston
 */
public class AdditionFactory<N extends Number> implements ReflexiveFunctionFactory<N> {
	private final BinaryValidator binaryValidator;
	private final Summor<N> summor;

	public AdditionFactory(Summor<N> summor) {
		this.binaryValidator = new BinaryValidator(Arrays.asList(Addition.PLUS_SYMBOL));
		this.summor = summor;
	}

	@Override
	public Addition<N> createElement(List<Token> tokens) throws FactoryException {
		ValidationResult result = binaryValidator.validate(tokens, null);
		if (result.get(0).equals(TOKEN) && result.get(1).equals(TOKEN)) {
			ReflexiveFunction<N> parameter1 = new IdentityFunction<>(tokens.get(0).getValue());
			ReflexiveFunction<N> parameter2 = new IdentityFunction<>(tokens.get(2).getValue());
			return new Addition<>(Arrays.asList(parameter1, parameter2), summor);
		}
		throw new FactoryException("Could not create Addition");
	}

	@Override
	public Function<N, N> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		ValidationResult result = binaryValidator.validate(tokens, functions);
		if (result.isValid()) {
			ReflexiveFunction<N> parameter1 = null;
			if (result.get(0).equals(TOKEN)) {
				parameter1 = new IdentityFunction<>(tokens.get(0).getValue());
			} else if (result.get(0).equals(FUNCTION)) {
				parameter1 = (ReflexiveFunction<N>) functions.get(0);
			}
			ReflexiveFunction<N> parameter2 = null;
			if (result.get(1).equals(TOKEN)) {
				parameter2 = new IdentityFunction<>(tokens.get(result.get(0).equals(TOKEN) ? 2 : 3).getValue());
			} else if (result.get(1).equals(FUNCTION)) {
				parameter2 = (ReflexiveFunction<N>) functions.get(1);
			}
			return new Addition<>(Arrays.asList(parameter1, parameter2), summor);
		} else {
			// todo: multary validator stuff
		}

		throw new FactoryException("Could not create Addition");
	}
}
