package logic.evaluable.constants;

import logic.Nameable;
import logic.evaluable.EvaluableFactory;
import logic.function.Function;
import logic.function.factory.construction.ValidatorAndConstructor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * A {@code Factory} for creating {@code LogicalConstant}s.
 * @author Steven Weston
 */
public class LogicalConstantFactory<T extends Nameable> extends EvaluableFactory<T> {
	public LogicalConstantFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, Boolean>>> getConstructors() {
		throw new NotImplementedException();
	}
}
