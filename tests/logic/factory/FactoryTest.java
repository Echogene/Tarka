package logic.factory;

import logic.TestClass;
import logic.function.Function;
import logic.function.reflexive.identity.IdentityFunction;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class FactoryTest<F extends Factory<?>> {
	protected List<Token> tokens;
	protected List<Function<?, ?>> functions;
	protected F factory;
	protected SimpleLogicLexerImpl lexer;

	public FactoryTest() {
		lexer = new SimpleLogicLexerImpl();
	}

	protected void setUpFunctions(String... identityFunctionParameters) {
		functions = new ArrayList<>();
		for (String identityFunctionParameter : identityFunctionParameters) {
			if (identityFunctionParameter == null || identityFunctionParameter.isEmpty()) {
				functions.add(null);
			} else {
				functions.add(new IdentityFunction<TestClass>(identityFunctionParameter));
			}
		}
	}

	protected void setUpTokens(String tokenString) throws Exception {
		tokens = lexer.tokeniseString(tokenString);
	}
}
