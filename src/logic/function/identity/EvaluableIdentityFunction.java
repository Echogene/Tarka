package logic.function.identity;

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

	EvaluableIdentityFunction(String parameter, Evaluable<T> function) {
		super(parameter, function);
	}

	public EvaluableIdentityFunction(Boolean bool) {
		super(bool ? "⊤" : "⊥");
	}

	@Override
	public EvaluableIdentityFunction<T> copy() {
		return new EvaluableIdentityFunction<>(parameter, (Evaluable<T>) function.copy());
	}
}
