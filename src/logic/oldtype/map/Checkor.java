package logic.oldtype.map;

/**
 * @author Steven Weston
 */
@FunctionalInterface
public interface Checkor<K> {

	void check(K k) throws CheckorException;
}
