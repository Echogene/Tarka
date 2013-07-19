package maths.number;

/**
 * @author Steven Weston
 */
public interface Subtractor<N extends Number> {
	N subtract(N minuend, N subtrahend);
}
