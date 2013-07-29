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
import static util.CollectionUtils.safeGet;

/**
 * @author Steven Weston
 */
public class MultaryConstructor<F extends Function<?, ?>, P extends Function<?, ?>> {
	private FunctionConstructorFromParameterList<F, P> functionConstructor;
	private ReflexiveFunctionConstructorFromString<? extends P> parameterConstructor;
	private int startIndex;

	public MultaryConstructor(FunctionConstructorFromParameterList<F, P> functionConstructor,
	                          ReflexiveFunctionConstructorFromString<? extends P> parameterConstructor) {
		this.functionConstructor  = functionConstructor;
		this.parameterConstructor = parameterConstructor;
		startIndex = 1;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public F construct(ValidationResult result, List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		int tokenIndex = startIndex;
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
		String operator = Token.valueOf(safeGet(tokens, startIndex - 1));
		return functionConstructor.construct(operator, parameterList);
	}
}
