package logic.identity;

import logic.Nameable;
import logic.function.evaluable.Evaluable;

/**
 * @author Steven Weston
 */
public class EvaluableIdentityFunction<T extends Nameable> extends IdentityFunction<T, Boolean> implements Evaluable<T> {

	public EvaluableIdentityFunction(String parameter) {
		super(parameter);
	}

	public EvaluableIdentityFunction(Evaluable<T> function) {
		super(function);
	}
}
