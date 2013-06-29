package maths.number;

import java.util.Collection;

/**
 * @author Steven Weston
 */
public interface Summor<N extends Number> {
	N add(N augand, N addend);

	N sum(Collection<N> summands);
}
