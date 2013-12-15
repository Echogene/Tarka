package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.set.SetFunction;
import logic.set.Set;

import java.util.List;

/**
 * @author Steven Weston
 */
public class SetFunctionDefinition<T extends Nameable> extends FunctionDefinition<T, Set<T>> {

	SetFunctionDefinition(String functionName, List<String> parameters, SetFunction<T> definition) {
		super(functionName, parameters, definition);
	}
}
