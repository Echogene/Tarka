package logic.function.factory;

import logic.factory.FactoryException;
import logic.function.Function;
import reading.lexing.Token;

import java.util.List;

import static logic.function.factory.ValidationResult.ValidationType.FUNCTION;
import static logic.function.factory.ValidationResult.ValidationType.TOKEN;

/**
 * @author Steven Weston
 */
public class BinaryConstructor<F extends Function<?, ?>, P extends Function<?, ?>, Q extends Function<?, ?>> {
	private FunctionConstructorFromTwoParameters<F, P, Q> functionConstructor;
	private ReflexiveFunctionConstructorFromString<? extends P> parameterConstructor1;
	private ReflexiveFunctionConstructorFromString<? extends Q> parameterConstructor2;

	public BinaryConstructor(
			FunctionConstructorFromTwoParameters<F, P, Q> functionConstructor,
			ReflexiveFunctionConstructorFromString<? extends P> parameterConstructor1,
			ReflexiveFunctionConstructorFromString<? extends Q> parameterConstructor2
	) {
		this.functionConstructor   = functionConstructor;
		this.parameterConstructor1 = parameterConstructor1;
		this.parameterConstructor2 = parameterConstructor2;
	}

	public F construct(ValidationResult result, List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		P parameter1 = null;
		boolean firstResultIsToken = result.get(0).equals(TOKEN);
		if (firstResultIsToken) {
			parameter1 = parameterConstructor1.construct(tokens.get(0).getValue());
		} else if (result.get(0).equals(FUNCTION)) {
			parameter1 = (P) functions.get(0);
		}
		Q parameter2 = null;
		if (result.get(1).equals(TOKEN)) {
			parameter2 = parameterConstructor2.construct(tokens.get(firstResultIsToken ? 2 : 3).getValue());
		} else if (result.get(1).equals(FUNCTION)) {
			parameter2 = (Q) functions.get(1);
		}
		String operatorString = tokens.get(firstResultIsToken ? 1 : 2).getValue();
		return functionConstructor.construct(parameter1, operatorString, parameter2);
	}
}
