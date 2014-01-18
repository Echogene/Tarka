package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.function.set.SetFunction;
import logic.set.Set;

import java.util.Map;

/**
 * @author Steven Weston
 */
public class DefinedSetFunction<T extends Nameable>
		extends AbstractDefinedFunction<T, Set<T>, DefinedSetFunction<T>>
		implements SetFunction<T, DefinedSetFunction<T>> {

	public DefinedSetFunction(String functionSymbol, SetFunction<T, ?> definition, Map<String, Function<T, ?, ?>> parameters) {
		super(functionSymbol, definition, parameters);
	}

	@Override
	public DefinedSetFunction<T> copy() {
		// todo: copy parameters?
		return new DefinedSetFunction<>(functionSymbol, (SetFunction<T, ?>) definition.copy(), parameters);
	}
}
