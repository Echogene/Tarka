package reading.reading;

import logic.type.TypeInferrorException;

/**
 * @author Steven Weston
 */
public interface Reader<T> {
	public T read(String string) throws ReadingException, TypeInferrorException;
}
