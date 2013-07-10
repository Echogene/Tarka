package logic.function.factory.multary;

import logic.factory.FactoryException;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface FunctionConstructorFromParameterList<F, P> {
	/**
	 * Construct an F from a list of Ps.
	 *
	 * @param operator
	 * @param parameterList
	 * @return
	 * @throws FactoryException
	 */
	F construct(String operator, List<P> parameterList) throws FactoryException;
}
