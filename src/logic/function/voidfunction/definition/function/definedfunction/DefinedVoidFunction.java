package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.function.voidfunction.VoidFunction;

import java.util.Map;

/**
 * @author Steven Weston
 */
public class DefinedVoidFunction<T extends Nameable> extends AbstractDefinedFunction<T, Void> implements VoidFunction<T> {

	public DefinedVoidFunction(VoidFunction<T> definition, Map<String, Function<T, ?>> parameters) {
		super(definition, parameters);
	}
}
