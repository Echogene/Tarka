package logic.set;

/**
 * @author Steven Weston
 */
public interface ModifiableSet<T> extends Set<T> {

	void put(T thing);
	T put(String string, T thing);
	T remove(String name);
}
