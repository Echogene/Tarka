package logic.function.factory.construction;

import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.ValidationException;
import logic.function.factory.validation.SimpleLogicValidator;
import reading.lexing.Token;

import java.util.List;

/**
 * @author Steven Weston
 */
public class ValidatorAndConstructor<F extends Function> {
	private final SimpleLogicValidator validator;
	private final Constructor<F> constructor;

	public ValidatorAndConstructor(SimpleLogicValidator validator, Constructor<F> constructor) {
		this.validator = validator;
		this.constructor = constructor;
	}

	public F construct(List<Token> tokens, List<Function<?, ?>> functions) throws ValidationException, FactoryException {
		return constructor.construct(validator.validate(tokens, functions));
	}
}
