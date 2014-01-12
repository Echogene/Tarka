package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;

import java.util.Map;

/**
 * @author Steven Weston
 */
public class DefinedReflexiveFunction<T extends Nameable> extends AbstractDefinedFunction<T, T> implements ReflexiveFunction<T> {

	public DefinedReflexiveFunction(String functionSymbol, ReflexiveFunction<T> definition, Map<String, Function<T, ?>> parameters) {
		super(functionSymbol, definition, parameters);
	}
}
