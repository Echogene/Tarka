package util.function;

/**
 * Like a {@link java.lang.Runnable} but can throw an Exception
 * @author Steven Weston
 */
@FunctionalInterface
public interface ExceptionalRunnable<E extends Exception> {

	void run() throws E;
}