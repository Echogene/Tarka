package maths.number;

/**
 * @author Steven Weston
 */
public interface Summor<N extends Number> {
	N add(N augend, N addend);

	N sum(Iterable<N> summands);
}
