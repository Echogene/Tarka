package logic.function.voidfunction.definition.member;

import logic.Nameable;
import logic.function.set.SetFunction;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class SetDefinition<T extends Nameable> extends Definition<T, Set<T>> {

	protected SetDefinition(String variableName, SetFunction<T> definition) {
		super(variableName, definition);
	}
}
