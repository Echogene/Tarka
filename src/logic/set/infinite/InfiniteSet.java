package logic.set.infinite;

import logic.Nameable;
import logic.set.NamedSet;

/**
 * @author Steven Weston
 */
public abstract class InfiniteSet<T extends Nameable> extends NamedSet<T> {
	public InfiniteSet(String name) {
		super(name);
	}
}
