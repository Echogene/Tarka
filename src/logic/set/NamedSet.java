package logic.set;

/**
 * @author Steven Weston
 */
public abstract class NamedSet<T> implements Set<T> {
	protected final String name;

	public NamedSet(String name) {
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
