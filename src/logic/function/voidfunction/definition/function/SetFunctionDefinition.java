package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.set.SetFunction;
import logic.set.Set;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Steven Weston
 */
public class SetFunctionDefinition<T extends Nameable> extends FunctionDefinition<T, Set<T>> {

	SetFunctionDefinition(String functionName, Map<String, java.util.Set<Type>> parameters, SetFunction<T> definition) {
		super(functionName, parameters, definition);
	}
}
