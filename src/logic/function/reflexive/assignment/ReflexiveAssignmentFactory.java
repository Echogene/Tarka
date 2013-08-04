package logic.function.reflexive.assignment;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.reflexive.ReflexiveFunctionFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author Steven Weston
 */
public class ReflexiveAssignmentFactory<T extends Nameable> extends ReflexiveFunctionFactory<T> {
	public static final String WHERE = "where";
	public static final String IS = "is";

	public ReflexiveAssignmentFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, T>>> getConstructors() {
		throw new NotImplementedException();
	}
}
