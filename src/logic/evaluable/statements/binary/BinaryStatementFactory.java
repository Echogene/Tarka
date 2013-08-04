package logic.evaluable.statements.binary;

import logic.Nameable;
import logic.evaluable.EvaluableFactory;
import logic.function.Function;
import logic.function.factory.construction.ValidatorAndConstructor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author Steven Weston
 */
public class BinaryStatementFactory<T extends Nameable> extends EvaluableFactory<T> {

	public BinaryStatementFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, Boolean>>> getConstructors() {
		throw new NotImplementedException();
	}
}
