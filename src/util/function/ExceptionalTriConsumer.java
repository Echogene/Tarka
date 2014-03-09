package util.function;

/**
 * @author Steven Weston
 */
@FunctionalInterface
public interface ExceptionalTriConsumer<T, U, V, E extends Exception> {

	void accept(T t, U u, V v) throws E;
}
