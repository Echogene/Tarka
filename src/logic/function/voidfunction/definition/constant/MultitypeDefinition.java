package logic.function.voidfunction.definition.constant;

import logic.Nameable;
import logic.function.Function;

/**
 * @author Steven Weston
 */
public class MultitypeDefinition<T extends Nameable> extends AbstractDefinition<T, Object, MultitypeDefinition<T>> {

	MultitypeDefinition(String variableName, Function<T, Object, ?> definition) {
		super(variableName, definition);
	}

	@Override
	public MultitypeDefinition<T> copy() {
		return new MultitypeDefinition<>(variableName, definition.copy());
	}
}
