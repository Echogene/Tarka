package util;

import util.function.ExceptionalTriConsumer;

import java.util.*;
import java.util.function.Function;

/**
 * @author Steven Weston
 */
public class CollectionUtils {
	/**
	 * Avoid throwing an error when accessing an out-of-bounds index in a list by returning null in that case.
	 * @param list The list whose item we are getting.
	 * @param index The index of the element we want.
	 * @param <T> The type of the elements in the list.
	 * @return The indexth element of the list or null if the index is out-of-bounds.
	 */
	public static <T> T safeGet(List<T> list, int index) {
		if (list == null || index >= list.size() || index < 0) {
			return null;
		}
		return list.get(index);
	}

	public static <T> T safeNext(Iterator<T> iterator) {
		if (iterator == null || !iterator.hasNext()) {
			return null;
		} else {
			return iterator.next();
		}
	}

	/**
	 * Get rid of the first and last members of a list.
	 * @param list
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> stripFirstAndLast(final List<T> list) {
		return list.subList(1, list.size() - 1);
	}

	public static String simpleNames(Collection<Class> classes) {
		List<String> output = new ArrayList<>();
		for (Class clazz : classes) {
			output.add(clazz.getSimpleName());
		}
		return "[" + StringUtils.join(output, ", ") + "]";
	}

	public static <T> T first(List<T> list) {
		return list.get(0);
	}

	public static <T> T last(List<T> list) {
		return list.get(list.size() - 1);
	}

	public static String arrayToString(Object[] objects, String afterEach) {
		StringBuilder sb = new StringBuilder();
		for (Object object : objects) {
			sb.append(object.toString());
			sb.append(afterEach);
		}
		return sb.toString();
	}

	public static <T, U> List<U> filterList(List<T> collection, Class<U> clazz) {
		List<U> output = new ArrayList<>();
		for (T t : collection) {
			if (clazz.isInstance(t)) {
				output.add(clazz.cast(t));
			}
		}
		return output;
	}

	@SafeVarargs
	public static <T> Set<T> createSet(T... elements) {
		HashSet<T> output = new HashSet<>();
		Collections.addAll(output, elements);
		return output;
	}

	public static <T> String toString(Collection<T> collection) {

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean first = true;
		for (T t : collection) {
			if (!first) {
				sb.append(", ");
			}
			sb.append(t.toString());
			first = false;
		}
		sb.append("]");
		return sb.toString();
	}

	public static <T> String debugToString(Collection<T> collection) {

		if (collection.size() > 10) {
			return "size = " + collection.size();
		}
		int count = 0;
		for (T t : collection) {
			count += t.toString().length();
		}
		if (count > 100) {
			return "size = " + collection.size();
		}
		return toString(collection);
	}

	/**
	 * From a collection, extract something from each of its members, but throw an error if it is not the same for all
	 * of them.
	 * @param collection  the collection from which to extract things
	 * @param function    the function that extracts a thing from each member
	 * @param ifDifferent throws an error if the extracted thing differs to the current thing.  It has access to the
	 *                    expected and actual things to generate the error message
	 * @param <T> the type of the extracted things
	 * @param <U> the type of the collection's members
	 * @param <E> the type of exception thrown
	 * @return the result of the extraction {@code function}
	 * @throws E if the extraction is ambiguous
	 */
	public static <T, U, E extends Exception> T extractUnique(
			Collection<U> collection,
			Function<U, T> function,
			ExceptionalTriConsumer<T, T, U, E> ifDifferent
	) throws E {

		T output = null;
		for (U u : collection) {
			T t = function.apply(u);
			if (output != null && !output.equals(t)) {
				ifDifferent.accept(output, t, u);
			}
			output = t;
		}
		return output;
	}
}
