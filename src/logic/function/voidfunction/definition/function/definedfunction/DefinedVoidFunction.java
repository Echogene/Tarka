package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.function.voidfunction.VoidFunction;

import java.util.Map;

/**
 * @author Steven Weston
 */
public class DefinedVoidFunction<T extends Nameable>
		extends AbstractDefinedFunction<T, Void, DefinedVoidFunction<T>, VoidFunction<T, ?>>
		implements VoidFunction<T, DefinedVoidFunction<T>> {

	public DefinedVoidFunction(String functionSymbol, VoidFunction<T, ?> definition, Map<String, Function<T, ?, ?>> parameters) {
		super(functionSymbol, definition, parameters);
	}

	@Override
	public DefinedVoidFunction<T> copy() {
		// todo: think about whether the parameters should be copied too
		return new DefinedVoidFunction<>(functionSymbol, definition.copy(), parameters);
	}
}
