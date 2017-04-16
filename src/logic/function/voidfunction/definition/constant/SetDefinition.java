package logic.function.voidfunction.definition.constant;

import logic.Nameable;
import logic.function.set.SetFunction;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class SetDefinition<T extends Nameable>
		extends AbstractDefinition<T, Set<T>, SetDefinition<T>, SetFunction<T, ?>> {

	public SetDefinition(String variableName, SetFunction<T, ?> definition) {
		super(variableName, definition);
	}

	@Override
	public SetDefinition<T> copy() {
		return new SetDefinition<>(variableName, definition.copy());
	}
}
