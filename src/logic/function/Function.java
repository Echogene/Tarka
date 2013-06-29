package logic.function;

import logic.Nameable;
import logic.set.Set;

/**
 * A {@code Function} represents a function from one class to another.  It is of the form F(a, b, ...).
 * @author Steven Weston
 */
public interface Function<D extends Nameable, C> {
	/**
	 * A function of the form f(a, b, ...) will evaluate when some values are bound to a, b &c.  The values get bound to
	 * the parameters by the Set, which should contain keys for 'a', 'b' &c.
	 *
	 * @param variables A Set that should contain the keys and values to be used by the function.
	 * @return The result of the function.
	 */
	@Deprecated
	public C evaluate(Set<? extends D> variables) throws ParameterNotFoundException;
}
