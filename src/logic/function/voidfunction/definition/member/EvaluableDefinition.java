package logic.function.voidfunction.definition.member;

import logic.Nameable;
import logic.function.evaluable.Evaluable;

/**
 * @author Steven Weston
 */
public class EvaluableDefinition<T extends Nameable> extends Definition<T, Boolean> {

	protected EvaluableDefinition(String variableName, Evaluable<T> definition) {
		super(variableName, definition);
	}
}
