package util.function;

/**
 * Like {@link java.util.function.BiConsumer} but can throw an exception.
 * @author Steven Weston
 */
public interface ExceptionalBiConsumer<T, U, E extends Exception> {

	void accept(T t, U u) throws E;
}
