package logic.type.map;

/**
 * @author Steven Weston
 */
@FunctionalInterface
public interface Testor<K> {

	boolean test(K k);
}
