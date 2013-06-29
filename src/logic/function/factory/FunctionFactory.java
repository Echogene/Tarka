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
public interface FunctionFactory<D extends Nameable, C> extends Factory<Function<D, C>> {
	public Function<D, C> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException;
}
