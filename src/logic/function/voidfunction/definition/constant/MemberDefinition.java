package logic.function.voidfunction.definition.constant;

import logic.Nameable;
import logic.function.reflexive.ReflexiveFunction;

/**
 * @author Steven Weston
 */
public class MemberDefinition<T extends Nameable> extends AbstractDefinition<T, T, MemberDefinition<T>> {

	public MemberDefinition(String variableName, ReflexiveFunction<T, ?> definition) {
		super(variableName, definition);
	}

	@Override
	public MemberDefinition<T> copy() {
		return new MemberDefinition<>(variableName, (ReflexiveFunction<T, ?>) definition.copy());
	}
}
