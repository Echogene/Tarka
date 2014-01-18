package logic.function.factory.validation.function;

import logic.Nameable;
import logic.function.Function;
import logic.type.map.MapToErrors;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface FunctionValidator<T extends Nameable> {

	MapToErrors<Function<T, ?, ?>> validateFunctions(List<Function<T, ?, ?>> functions) throws FunctionValidationException;
}
