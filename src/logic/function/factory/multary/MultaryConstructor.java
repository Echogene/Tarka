package logic.function.factory.multary;

import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.ReflexiveFunctionConstructorFromString;
import logic.function.factory.ValidationResult;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static logic.function.factory.ValidationResult.ValidationType.FUNCTION;
import static logic.function.factory.ValidationResult.ValidationType.TOKEN;

/**
 * @author Steven Weston
 */
public class MultaryConstructor<F extends Function<?, ?>, P extends Function<?, ?>> {
	private FunctionConstructorFromParameterList<F, P> functionConstructor;
	private ReflexiveFunctionConstructorFromString<? extends P> parameterConstructor;

	public MultaryConstructor(FunctionConstructorFromParameterList<F, P> functionConstructor,
	                          ReflexiveFunctionConstructorFromString<? extends P> parameterConstructor) {
		this.functionConstructor  = functionConstructor;
		this.parameterConstructor = parameterConstructor;
	}

	public F construct(ValidationResult result, List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		int tokenIndex = 1;
		int functionIndex = 0;
		java.util.List<P> parameterList = new ArrayList<>();
		for (ValidationResult.ValidationType type : result) {
			if (type.equals(TOKEN)) {
				parameterList.add(parameterConstructor.construct(tokens.get(tokenIndex++).getValue()));
				functionIndex++;
			} else if (type.equals(FUNCTION)) {
				parameterList.add((P) functions.get(functionIndex++));
				tokenIndex += 2;
			}
		}
		return functionConstructor.construct(tokens.get(0).getValue(), parameterList);
	}
}
