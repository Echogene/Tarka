package logic.function.evaluable.predicate.equality;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.predicate.Predicate;
import logic.model.Model;

import java.util.Map;

/**
 * A class that represents an equality predicate.  An equality predicate is of the form "x = y" and either evaluates to
 * true or false, depending on whether "x" represents the same thing as "y".  It can also take the form "f = g" for
 * arbitrary reflexive (on the universe of discourse) functions "f" and "g".  In this case, it returns true when the
 * evaluations of "f" and "g" are the same thing.
 * @author Steven Weston
 */
public class EqualityPredicate<T extends Nameable> extends Predicate<T, EqualityPredicate<T>> {

	/**
	 * The symbol used to represent equality.  Surprisingly, it is an equals symbol.
	 */
	public static final String EQUALITY_SYMBOL = "=";

	/**
	 * The function that precedes the equals symbol in the expression.
	 */
	private final Function<T, ?, ?> equorFunction;

	/**
	 * The function that follows the equals symbol in the expression.
	 */
	private final Function<T, ?, ?> equandFunction;

	public Function<T, ?, ?> getEquandFunction() {
		return equandFunction;
	}

	public Function<T, ?, ?> getEquorFunction() {
		return equorFunction;
	}

	/**
	 * Construct an {@code EqualityPredicate} "f = g" from two functions "f" and "g".
	 * @param equorFunction The function that precedes the equals symbol.
	 * @param equandFunction The function that follows the equals symbol.
	 */
	public EqualityPredicate(Function<T, ?, ?> equorFunction, Function<T, ?, ?> equandFunction) {
		this.equorFunction  = equorFunction;
		this.equandFunction = equandFunction;
	}

	@Override
	public Boolean evaluate(Model<T, ?, ?> model) throws Exception {
		Object equor = getEquorFunction().evaluate(model);
		Object equand = getEquandFunction().evaluate(model);
		return equor.equals(equand);
	}

	@Override
	public void reduce(Map<String, Function<T, ?, ?>> reductions) {
		equorFunction.reduce(reductions);
		equandFunction.reduce(reductions);
	}

	@Override
	public String toString() {
		return "(" + getEquorFunction().toString() + " " + EQUALITY_SYMBOL + " " + getEquandFunction().toString() + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof EqualityPredicate<?>)) {
			return false;
		}
		EqualityPredicate<?> other = (EqualityPredicate<?>) o;
		return getEquorFunction().equals(other.getEquorFunction())
				&& getEquandFunction().equals(other.getEquandFunction());
	}

	@Override
	public EqualityPredicate<T> copy() {
		return new EqualityPredicate<>(equorFunction.copy(), equandFunction.copy());
	}
}
