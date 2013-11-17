package logic.function.voidfunction.definition.member;

import logic.Nameable;
import logic.function.reflexive.ReflexiveFunction;

/**
 * @author Steven Weston
 */
public class MemberDefinition<T extends Nameable> extends Definition<T, T> {

	public MemberDefinition(String variableName, ReflexiveFunction<T> definition) {
		super(variableName, definition);
	}
}
