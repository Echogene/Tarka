package logic.function.ifelse;

import logic.Nameable;
import logic.function.evaluable.Evaluable;

/**
 * @author Steven Weston
 */
public class EvaluableIfElse<T extends Nameable> extends IfElse<T, Boolean> implements Evaluable<T> {

	public EvaluableIfElse(Evaluable<T> condition, Evaluable<T> ifTrue, Evaluable<T> ifFalse) {
		super(condition, ifTrue, ifFalse);
	}
}
