package logic;

/**
 * A class representing a connective that "connects" one or more expressions.  Examples are unary connectives such as
 * NOT or quantifiers and binary connectives such as AND or OR.
 * @author Steven Weston
 */
public abstract class Connective {
	/**
	 * An interface representing the type of the {@code Connective}.  It is used for distinguishing, comparing and
	 * grouping similar connectives.
	 */
	public interface ConnectiveType {}

	/**
	 * The stored type of the {@code Connective}.
	 */
	protected ConnectiveType type;

	/**
	 * Get the type of the {@code Connective}.
	 * @return The type of the {@code Connective}.
	 */
	public ConnectiveType getType() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Connective)) {
			return false;
		}
		Connective other = (Connective) o;
		return getType() == (other.getType());
	}
}
