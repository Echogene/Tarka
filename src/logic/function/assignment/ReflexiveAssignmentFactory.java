package logic.function.assignment;

import logic.Nameable;
import logic.function.reflexive.ReflexiveFunctionFactory;

/**
 * @author Steven Weston
 */
public class ReflexiveAssignmentFactory<T extends Nameable> extends ReflexiveFunctionFactory<T, Assignment<T>> {

	public static final String WHERE = "where";
	public static final String IS = "is";

	public ReflexiveAssignmentFactory() {
		super(getConstructors());
	}
}
