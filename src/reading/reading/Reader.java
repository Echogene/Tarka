package reading.reading;

/**
 * @author Steven Weston
 */
public interface Reader<T> {
	public T read(String string) throws ReadingException;
}
