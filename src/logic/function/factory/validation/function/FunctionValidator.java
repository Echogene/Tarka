package logic.function.factory.validation.function;

import logic.function.Function;
import logic.function.factory.validation.token.TokenValidationException;
import logic.type.map.MapToErrors;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface FunctionValidator {

	MapToErrors<Function<?, ?>> validate(List<Function<?, ?>> tokens) throws TokenValidationException;
}
