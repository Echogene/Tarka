package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.reflexive.ReflexiveFunction;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author Steven Weston
 */
public class ReflexiveFunctionDefinition<T extends Nameable>
		extends FunctionDefinition<T, T, ReflexiveFunctionDefinition<T>, ReflexiveFunction<T, ?>> {

	public ReflexiveFunctionDefinition(String functionName, LinkedHashMap<String, Set<Type>> parameters, ReflexiveFunction<T, ?> definition) {
		super(functionName, parameters, definition);
	}

	@Override
	public ReflexiveFunctionDefinition<T> copy() {
		return new ReflexiveFunctionDefinition<>(functionSymbol, parameters, definition.copy());
	}
}
