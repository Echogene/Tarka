package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;
import util.FunctionUtils;

import java.util.Map;

/**
 * @author Steven Weston
 */
public class DefinedReflexiveFunction<T extends Nameable>
		extends AbstractDefinedFunction<T, T, DefinedReflexiveFunction<T>, ReflexiveFunction<T, ?>>
		implements ReflexiveFunction<T, DefinedReflexiveFunction<T>> {

	public DefinedReflexiveFunction(String functionSymbol, ReflexiveFunction<T, ?> definition, Map<String, Function<T, ?, ?>> parameters) {
		super(functionSymbol, definition, parameters);
	}

	@Override
	public DefinedReflexiveFunction<T> copy() {
		return new DefinedReflexiveFunction<>(functionSymbol, definition.copy(), FunctionUtils.copy(parameters));
	}
}
