package logic.factory;

import logic.function.Function;
import logic.function.factory.FunctionFactory;
import reading.lexing.LexerException;
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
}
