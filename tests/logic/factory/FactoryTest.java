package logic.factory;

import logic.TestClass;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.set.identity.SetIdentityFunction;
import reading.lexing.LexerException;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * @author Steven Weston
 */
public abstract class FactoryTest<F extends FunctionFactory<?, ?>> {

	protected List<Token> tokens;
	protected List<Function<?, ?>> functions;
	protected F factory;
	protected SimpleLogicLexerImpl lexer;
	protected FunctionFactory<?, ?> functionFactory;

	public FactoryTest() {
		lexer = new SimpleLogicLexerImpl();
	}

	protected void setUpFunctions(String... functions) throws LexerException, FactoryException {
		this.functions = new ArrayList<>();
		for (String function : functions) {
			if (function == null || function.isEmpty()) {
				this.functions.add(null);
			} else {
				this.functions.add(functionFactory.createElement(lexer.tokeniseString(function)));
			}
		}
	}

	protected void setUpTokens(String tokenString) throws Exception {
		tokens = lexer.tokeniseString(tokenString);
	}

	protected void setUpSetIdentityFunction(String identityFunctionParameter) {
		functions = new ArrayList<>(1);
		if (identityFunctionParameter == null || identityFunctionParameter.isEmpty()) {
			functions.add(null);
		} else {
			functions.add(new SetIdentityFunction<>(identityFunctionParameter));
		}
	}

	protected void setUpOneIdentityAndOneSetIdentityFunction(String identityFunctionParameter1, String identityFunctionParameter2) {
		functions = new ArrayList<>(2);
		if (!identityFunctionParameter1.isEmpty()) {
			functions.add(new IdentityFunction<TestClass>(identityFunctionParameter1));
		}
		if (!identityFunctionParameter2.isEmpty()) {
			functions.add(new SetIdentityFunction<>(identityFunctionParameter2));
		}
	}

	protected void setUpIdentityFunction(String identityFunctionParameter) {
		functions = new ArrayList<>(1);
		if (identityFunctionParameter == null || identityFunctionParameter.isEmpty()) {
			functions.add(null);
		} else {
			functions.add(new IdentityFunction<TestClass>(identityFunctionParameter));
		}
	}

	protected void expectFactoryException() {
		try {
			factory.createElement(tokens, functions);
			fail("Should not have been able to create");
		} catch (FactoryException e) {}
	}
}
