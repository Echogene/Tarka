package logic.function.voidfunction.definition.constant;

import logic.Nameable;
import logic.function.evaluable.Evaluable;

/**
 * @author Steven Weston
 */
public class EvaluableDefinition<T extends Nameable> extends Definition<T, Boolean> {

	public EvaluableDefinition(String variableName, Evaluable<T> definition) {
		super(variableName, definition);
	}
}
