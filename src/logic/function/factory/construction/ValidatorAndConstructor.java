package logic.function.factory.construction;

import logic.function.Function;
import logic.function.factory.ValidationException;
import logic.function.factory.validation.Validator;
import reading.lexing.Token;

import java.util.List;

/**
 * @author Steven Weston
 */
public class ValidatorAndConstructor<F extends Function> {
	private final Validator validator;
	private final Constructor<F> constructor;

	public ValidatorAndConstructor(Validator validator, Constructor<F> constructor) {
		this.validator = validator;
		this.constructor = constructor;
	}

	public F construct(List<Token> tokens, List<Function<?, ?>> functions) throws ValidationException {
		return constructor.construct(validator.validate(tokens, functions));
	}
}
