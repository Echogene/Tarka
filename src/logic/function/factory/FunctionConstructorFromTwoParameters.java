package logic.function.factory;

/**
 * @author Steven Weston
 */
public interface FunctionConstructorFromTwoParameters<F, P, Q> {
	/**
	 * Construct an F from a P and a Q.
	 * @param parameter1
	 * @param parameter2
	 * @return
	 */
	F construct(P parameter1, Q parameter2);
}
