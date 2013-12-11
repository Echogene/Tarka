package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.reflexive.ReflexiveFunction;

import java.util.List;

/**
 * @author Steven Weston
 */
public class ReflexiveFunctionDefinition<T extends Nameable> extends FunctionDefinition<T, T> {

	ReflexiveFunctionDefinition(String functionName, List<String> parameters, ReflexiveFunction<T> definition) {
		super(functionName, parameters, definition);
	}
}
