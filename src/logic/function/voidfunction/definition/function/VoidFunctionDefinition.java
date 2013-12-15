package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.voidfunction.VoidFunction;

import java.util.List;

/**
 * @author Steven Weston
 */
public class VoidFunctionDefinition<T extends Nameable> extends FunctionDefinition<T, Void> {

	VoidFunctionDefinition(String functionName, List<String> parameters, VoidFunction<T> definition) {
		super(functionName, parameters, definition);
	}
}
