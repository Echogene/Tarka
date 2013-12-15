package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.evaluable.Evaluable;

import java.util.List;

/**
 * @author Steven Weston
 */
public class EvaluableDefinition<T extends Nameable> extends FunctionDefinition<T, Boolean> {

	EvaluableDefinition(String functionName, List<String> parameters, Evaluable<T> definition) {
		super(functionName, parameters, definition);
	}
}
