package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.voidfunction.VoidFunction;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author Steven Weston
 */
public class VoidFunctionDefinition<T extends Nameable> extends FunctionDefinition<T, Void> {

	VoidFunctionDefinition(String functionName, Map<String, Set<Type>> parameters, VoidFunction<T> definition) {
		super(functionName, parameters, definition);
	}
}
