package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.evaluable.Evaluable;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author Steven Weston
 */
public class EvaluableDefinition<T extends Nameable>
		extends FunctionDefinition<T, Boolean, EvaluableDefinition<T>, Evaluable<T, ?>> {

	EvaluableDefinition(String functionName, LinkedHashMap<String, Set<Type>> parameters, Evaluable<T, ?> definition) {
		super(functionName, parameters, definition);
	}

	@Override
	public EvaluableDefinition<T> copy() {
		return new EvaluableDefinition<>(functionSymbol, parameters, definition.copy());
	}
}
