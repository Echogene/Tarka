package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.function.set.SetFunction;
import logic.set.Set;

import java.util.Map;

/**
 * @author Steven Weston
 */
public class DefinedSetFunction<T extends Nameable> extends AbstractDefinedFunction<T, Set<T>> implements SetFunction<T> {

	public DefinedSetFunction(SetFunction<T> definition, Map<String, Function<T, ?>> parameters) {
		super(definition, parameters);
	}
}
