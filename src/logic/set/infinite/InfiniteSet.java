package logic.set.infinite;

import logic.Nameable;
import logic.set.NamedSet;
import logic.set.UnmodifiableSet;

/**
 * @author Steven Weston
 */
public abstract class InfiniteSet<T extends Nameable> extends NamedSet<T> implements UnmodifiableSet<T> {

	protected InfiniteSet(String name) {
		super(name);
	}
}
