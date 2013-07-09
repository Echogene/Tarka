package logic.function.factory;

import logic.factory.FactoryException;

/**
 * @author Steven Weston
 */
public interface FunctionConstructorFromTwoParameters<F, P, Q> {
	/**
	 * Construct an F from a P and a Q.
	 *
	 * @param parameter1
	 * @param operator
	 *@param parameter2  @return
	 */
	F construct(P parameter1, String operator, Q parameter2) throws FactoryException;
}
