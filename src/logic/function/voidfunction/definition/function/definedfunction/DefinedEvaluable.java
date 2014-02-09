package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import util.FunctionUtils;

import java.util.Map;

/**
 * @author Steven Weston
 */
public class DefinedEvaluable<T extends Nameable>
		extends AbstractDefinedFunction<T, Boolean, DefinedEvaluable<T>, Evaluable<T, ?>>
		implements Evaluable<T, DefinedEvaluable<T>> {

	public DefinedEvaluable(String functionSymbol, Evaluable<T, ?> definition, Map<String, Function<T, ?, ?>> parameters) {
		super(functionSymbol, definition, parameters);
	}

	@Override
	public DefinedEvaluable<T> copy() {
		return new DefinedEvaluable<>(functionSymbol, definition.copy(), FunctionUtils.copy(parameters));
	}
}
