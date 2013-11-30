package logic.set.undeterminable;

import logic.Nameable;
import logic.set.NamedSet;
import logic.set.UnmodifiableSet;

/**
 * A set that we cannot determine whether it is finite or infinite.
 * @author Steven Weston
 */
public abstract class UndeterminableSet<T extends Nameable> extends NamedSet<T> implements UnmodifiableSet<T> {

	protected UndeterminableSet(String name) {
		super(name);
	}
}
