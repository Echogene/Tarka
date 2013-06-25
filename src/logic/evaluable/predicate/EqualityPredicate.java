package logic.evaluable.predicate;

import logic.Nameable;
import logic.function.ParameterNotFoundException;
import logic.function.reflexive.IdentityFunction;
import logic.function.reflexive.ReflexiveFunction;
import logic.model.universe.Universe;
import logic.set.Set;

/**
 * A class that represents an equality predicate.  An equality predicate is of the form "x = y" and either evaluates to
 * true or false, depending on whether "x" represents the same thing as "y".  It can also take the form "f = g" for
 * arbitrary reflexive (on the universe of discourse) functions "f" and "g".  In this case, it returns true when the
 * evaluations of "f" and "g" are the same thing.
 * @author Steven Weston
 */
public class EqualityPredicate<T extends Nameable> extends Predicate<T> {
	/**
	 * The symbol used to represent equality.  Surprisingly, it is an equals symbol.
	 */
	public static final String EQUALITY_STRING = "=";

	/**
	 * The function that precedes the equals symbol in the expression.
	 */
	protected ReflexiveFunction<T> equorFunction;

	/**
	 * The function that follows the equals symbol in the expression.
	 */
	protected ReflexiveFunction<T> equandFunction;

	public ReflexiveFunction<T> getEquandFunction() {
		return equandFunction;
	}

	public ReflexiveFunction<T> getEquorFunction() {
		return equorFunction;
	}

	/**
	 * Construct an {@code EqualityPredicate} "x = y" from two symbols "x" and "y".
	 * @param equorSymbol The symbol that precedes the equals symbol.
	 * @param equandSymbol The symbol that follows the equals symbol.
	 */
	public EqualityPredicate(String equorSymbol, String equandSymbol) {
		this.equorFunction  = new IdentityFunction<>(equorSymbol);
		this.equandFunction = new IdentityFunction<>(equandSymbol);
	}

	/**
	 * Construct an {@code EqualityPredicate} "f = y" from a function "f" and a symbol "y".
	 * @param equorFunction The function that precedes the equals symbol.
	 * @param equandSymbol The symbol that follows the equals symbol.
	 */
	public EqualityPredicate(ReflexiveFunction<T> equorFunction, String equandSymbol) {
		this.equorFunction  = equorFunction;
		this.equandFunction = new IdentityFunction<>(equandSymbol);
	}

	/**
	 * Construct an {@code EqualityPredicate} "x = g" from a symbol "x" and a function "g".
	 * @param equorSymbol The symbol that precedes the equals symbol.
	 * @param equandFunction The function that follows the equals symbol.
	 */
	public EqualityPredicate(String equorSymbol, ReflexiveFunction<T> equandFunction) {
		this.equorFunction  = new IdentityFunction<>(equorSymbol);
		this.equandFunction = equandFunction;
	}

	/**
	 * Construct an {@code EqualityPredicate} "f = g" from two functions "f" and "g".
	 * @param equorFunction The function that precedes the equals symbol.
	 * @param equandFunction The function that follows the equals symbol.
	 */
	public EqualityPredicate(ReflexiveFunction<T> equorFunction, ReflexiveFunction<T> equandFunction) {
		this.equorFunction  = equorFunction;
		this.equandFunction = equandFunction;
	}

	@Override
	public Boolean evaluate(Set<? extends T> set) throws ParameterNotFoundException {
		return getEquorFunction().evaluate(set).equals(getEquandFunction().evaluate(set));
	}

	@Override
	public Boolean evaluate(Universe<T> universe) throws Exception {
		T equor = getEquorFunction().evaluate(universe);
		T equand = getEquandFunction().evaluate(universe);
		return equor.equals(equand);
	}

	@Override
	public String toString() {
		return "(" + getEquorFunction().toString() + " " + EQUALITY_STRING + " " + getEquandFunction().toString() + ")";
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
	public String getName() {
		return toString();
	}
}
