package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.voidfunction.VoidFunction;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author Steven Weston
 */
public class VoidFunctionDefinition<T extends Nameable>
		extends FunctionDefinition<T, Void, VoidFunctionDefinition<T>, VoidFunction<T, ?>> {

	VoidFunctionDefinition(String functionName, LinkedHashMap<String, Set<Type>> parameters, VoidFunction<T, ?> definition) {
		super(functionName, parameters, definition);
	}

	@Override
	public VoidFunctionDefinition<T> copy() {
		return new VoidFunctionDefinition<>(functionSymbol, parameters, definition.copy());
	}
}
