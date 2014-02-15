package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.set.SetFunction;
import logic.set.Set;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

/**
 * @author Steven Weston
 */
public class SetFunctionDefinition<T extends Nameable>
		extends FunctionDefinition<T, Set<T>, SetFunctionDefinition<T>, SetFunction<T, ?>> {

	SetFunctionDefinition(String functionName, LinkedHashMap<String, java.util.Set<Type>> parameters, SetFunction<T, ?> definition) {
		super(functionName, parameters, definition);
	}

	@Override
	public SetFunctionDefinition<T> copy() {
		return new SetFunctionDefinition<>(functionSymbol, parameters, definition.copy());
	}
}
