package logic.function.voidfunction.definition.constant;

import logic.Nameable;
import logic.function.evaluable.Evaluable;

/**
 * @author Steven Weston
 */
public class BooleanDefinition<T extends Nameable> extends AbstractDefinition<T, Boolean, BooleanDefinition<T>> {

	public BooleanDefinition(String variableName, Evaluable<T, ?> definition) {
		super(variableName, definition);
	}

	@Override
	public BooleanDefinition<T> copy() {
		return new BooleanDefinition<>(variableName, (Evaluable<T, ?>) definition.copy());
	}
}
