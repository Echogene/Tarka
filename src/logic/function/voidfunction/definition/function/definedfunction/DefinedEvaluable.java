package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;

import java.util.Map;

/**
 * @author Steven Weston
 */
public class DefinedEvaluable<T extends Nameable>
		extends AbstractDefinedFunction<T, Boolean, DefinedEvaluable<T>>
		implements Evaluable<T, DefinedEvaluable<T>> {

	public DefinedEvaluable(String functionSymbol, Evaluable<T, ?> definition, Map<String, Function<T, ?, ?>> parameters) {
		super(functionSymbol, definition, parameters);
	}

	@Override
	public DefinedEvaluable<T> copy() {
		// todo: should parameters be copied?
		return new DefinedEvaluable<>(functionSymbol, (Evaluable<T, ?>) definition.copy(), parameters);
	}
}
