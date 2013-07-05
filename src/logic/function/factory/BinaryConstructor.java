package logic.function.factory;

import logic.function.Function;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static logic.function.factory.ValidationResult.ValidationType.FUNCTION;
import static logic.function.factory.ValidationResult.ValidationType.TOKEN;

/**
 * @author Steven Weston
 */
public class BinaryConstructor<F extends Function<?, ?>, G extends Function<?, ?>> {
	private ReflexiveFunctionConstructorFromString<? extends F> identityConstructor;
	private FunctionConstructorFromParameterList<F, G> functionConstructor;

	public BinaryConstructor(
			ReflexiveFunctionConstructorFromString<? extends F> identityConstructor,
			FunctionConstructorFromParameterList<F, G> functionConstructor
	) {
		this.identityConstructor = identityConstructor;
		this.functionConstructor = functionConstructor;
	}

	private List<F> getParameterList(ValidationResult result, List<Token> tokens, List<Function<?, ?>> functions) {
		java.util.List<F> list = new ArrayList<>();
		F parameter1 = null;
		if (result.get(0).equals(TOKEN)) {
			parameter1 = identityConstructor.construct(tokens.get(0).getValue());
		} else if (result.get(0).equals(FUNCTION)) {
			parameter1 = (F) functions.get(0);
		}
		list.add(parameter1);
		F parameter2 = null;
		if (result.get(1).equals(TOKEN)) {
			parameter2 = identityConstructor.construct(tokens.get(result.get(0).equals(TOKEN) ? 2 : 3).getValue());
		} else if (result.get(1).equals(FUNCTION)) {
			parameter2 = (F) functions.get(1);
		}
		list.add(parameter2);
		return list;
	}

	public G construct(ValidationResult result, List<Token> tokens, List<Function<?, ?>> functions) {
		return functionConstructor.construct(getParameterList(result, tokens, functions));
	}
}
