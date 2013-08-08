package logic.function.factory.construction;

import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.validation.results.ValidationResult;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface Constructor<F extends Function> {
	F construct(List<ValidationResult> results) throws FactoryException;
}
