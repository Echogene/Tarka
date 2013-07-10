package maths.number.integer.functions;

import logic.TestClass;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import logic.function.reflexive.IdentityFunction;
import logic.function.reflexive.ReflexiveFunction;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSummor;
import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class AdditionFactoryTest {
	private static List<Token> tokens;
	private static List<Function<?, ?>> functions;
	private static SimpleLogicLexerImpl lexer;
	private static AdditionFactory<Integer> factory;
	private static IntegerSummor summor;

	@BeforeClass
	public static void setUp() {
		lexer   = new SimpleLogicLexerImpl();
		summor = new IntegerSummor();
		factory = new AdditionFactory<>(summor);
	}

	@Test
	public void testFactoryWithoutFunctions() throws Exception {
		ReflexiveFunction<Integer> two = new IdentityFunction<>("2");
		ReflexiveFunction<Integer> three = new IdentityFunction<>("3");
		List<ReflexiveFunction<Integer>> parameters = new ArrayList<>();
		parameters.add(two);
		parameters.add(three);

		Addition<Integer> expected = new Addition<>(parameters, summor);

		setUpTokens("2+3");
		Addition<Integer> actual = (Addition<Integer>) factory.createElement(tokens);

		assertEquals(expected, actual);
	}

	@Test
	public void testMultary() throws Exception {
		ReflexiveFunction<Integer> two = new IdentityFunction<>("2");
		ReflexiveFunction<Integer> three = new IdentityFunction<>("3");
		ReflexiveFunction<Integer> four = new IdentityFunction<>("4");
		List<ReflexiveFunction<Integer>> parameters = new ArrayList<>();
		parameters.add(two);
		parameters.add(three);
		parameters.add(four);

		Addition<Integer> expected = new Addition<>(parameters, summor);

		setUpTokens("Σ 2 3 4");
		setUpFunctions("", "", "");
		Addition<Integer> actual = (Addition<Integer>) factory.createElement(tokens, functions);

		assertEquals(expected, actual);
	}

	private void setUpFunctions(String... identityFunctionParameters) {
		functions = new ArrayList<>();
		for (String identityFunctionParameter : identityFunctionParameters) {
			if (identityFunctionParameter == null || identityFunctionParameter.isEmpty()) {
				functions.add(null);
			} else {
				functions.add(new IdentityFunction<TestClass>(identityFunctionParameter));
			}
		}
	}

	private void setUpTokens(String tokenString) throws Exception {
		tokens = lexer.tokeniseString(tokenString);
	}
}
