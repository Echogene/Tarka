package logic.function.factory;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface FunctionConstructorFromParameterList<F, G> {
	G construct(List<F> parameters);
}
