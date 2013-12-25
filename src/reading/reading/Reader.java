package reading.reading;

import logic.type.TypeInferrorException;

/**
 * @author Steven Weston
 */
public interface Reader<T> {

	<F extends T> F read(String string) throws ReadingException, TypeInferrorException;
}
