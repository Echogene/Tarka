package logic.set.finite;

/**
 * @author Steven Weston
 */
public interface Filter<T> {

	boolean passesThrough(T t) throws Exception;
}
