package util;

/**
 * @author Steven Weston
 */
public class ClassUtils {

	public static String safeSimpleName(Class clazz) {
		if (clazz == null) {
			return null;
		} else {
			return clazz.getSimpleName();
		}
	}
}
