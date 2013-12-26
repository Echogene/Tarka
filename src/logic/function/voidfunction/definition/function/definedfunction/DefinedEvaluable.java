package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;

import java.util.Map;

/**
 * @author Steven Weston
 */
public class DefinedEvaluable<T extends Nameable> extends AbstractDefinedFunction<T, Boolean> implements Evaluable<T> {

	public DefinedEvaluable(Evaluable<T> definition, Map<String, Function<T, ?>> parameters) {
		super(definition, parameters);
	}
}
