package logic.type.map;

/**
 * @author Steven Weston
 */
public interface Checkor<K> {

	void check(K k) throws CheckorException;
}
