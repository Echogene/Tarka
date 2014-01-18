package logic.function.ifelse;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.model.Model;

import java.util.Map;

import static java.text.MessageFormat.format;

/**
 * @author Steven Weston
 */
public abstract class AbstractIfElse<D extends Nameable, C, F extends AbstractIfElse<D, C, F>>
		implements Function<D, C, F> {

	public static final String IF = "if";
	public static final String OTHERWISE = "otherwise";

	final Evaluable<D, ?> condition;
	final Function<D, C, ?> ifTrue;
	final Function<D, C, ?> ifFalse;

	AbstractIfElse(Evaluable<D, ?> condition, Function<D, C, ?> ifTrue, Function<D, C, ?> ifFalse) {
		this.condition = condition;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
	}

	@Override
	public C evaluate(Model<D, ?, ?> model) throws Exception {
		if (condition.evaluate(model)) {
			return ifTrue.evaluate(model);
		} else {
			return ifFalse.evaluate(model);
		}
	}

	@Override
	public void reduce(Map<String, Function<D, ?, ?>> reductions) {
		condition.reduce(reductions);
		ifTrue.reduce(reductions);
		ifFalse.reduce(reductions);
	}

	@Override
	public String toString() {
		return format("({0} {1} {2} {3} {4})", ifTrue, IF, condition, OTHERWISE, ifFalse);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof AbstractIfElse)) {
			return false;
		}
		AbstractIfElse<?, ?, ?> other = (AbstractIfElse<?, ?, ?>) o;
		return condition.equals(other.condition)
				&& ifTrue.equals(other.ifTrue)
				&& ifFalse.equals(other.ifFalse);
	}
}
