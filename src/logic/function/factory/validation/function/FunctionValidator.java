package logic.function.factory.validation.function;

import logic.function.Function;
import logic.type.map.MapToErrors;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface FunctionValidator {

	MapToErrors<Function<?, ?>> validateFunctions(List<Function<?, ?>> functions) throws FunctionValidationException;
}
