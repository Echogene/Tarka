package logic.set;

import logic.Nameable;

/**
 * A {@code NamedSet} is a {@code HashMap} that has a name.
 * @author Steven Weston
 */
public abstract class NamedSet<T extends Nameable> implements Set<T> {
	protected String name;

	public NamedSet(String name) {
		this.name = name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getName() {
		return name;
	}
}
