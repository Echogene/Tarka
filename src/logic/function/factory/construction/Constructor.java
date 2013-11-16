package logic.function.factory.construction;

import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.oldvalidation.results.ValidationResult;

import java.util.List;

/**
 * @author Steven Weston
 * @deprecated
 */
public interface Constructor<F extends Function> {
	F construct(List<ValidationResult> results) throws FactoryException;
}
