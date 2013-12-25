package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.reflexive.ReflexiveFunction;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author Steven Weston
 */
public class ReflexiveFunctionDefinition<T extends Nameable> extends FunctionDefinition<T, T> {

	ReflexiveFunctionDefinition(String functionName, Map<String, Set<Type>> parameters, ReflexiveFunction<T> definition) {
		super(functionName, parameters, definition);
	}
}
