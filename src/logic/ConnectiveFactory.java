package logic;

import logic.factory.Factory;
import logic.factory.FactoryException;

/**
 * A {@code Factory} for creating {@code Connective}s.  It contains an additional function for creating
 * {@code Connective}s from {@code String}s.
 * @author Steven Weston
 */
public interface ConnectiveFactory<T extends Connective> extends Factory<T> {
	/**
	 * Create a {@code Connective} specified by a {@code String}.
	 * @param string The {@code String} with which to create the {@code Connective}.
	 * @return The created {@code Connective}.
	 * @throws FactoryException if the {@code Factory} cannot create the {@code Connective}.
	 */
	public T createElement(String string) throws FactoryException;
}
