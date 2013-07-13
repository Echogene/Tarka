package logic.function.factory;

import logic.Nameable;
import logic.factory.Factory;
import logic.factory.FactoryException;
import logic.function.Function;
import reading.lexing.Token;

import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class FunctionFactory<D extends Nameable, C> implements Factory<Function<D, C>> {
	@Override
	public Function<D, C> createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}

	public abstract Function<D, C> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException;
}
