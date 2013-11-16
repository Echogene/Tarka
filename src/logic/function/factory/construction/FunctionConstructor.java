package logic.function.factory.construction;

import logic.function.Function;
import reading.lexing.Token;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface FunctionConstructor<F> {

	F construct(List<Token> tokens, List<Function<?, ?>> functions);
}
