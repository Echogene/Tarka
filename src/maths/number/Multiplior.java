package maths.number;

/**
 * @author Steven Weston
 */
public interface Multiplior<N extends Number> {

	N multiply(N multiplier, N multiplicand);

	N produce(Iterable<N> factors);
}
