package logic.function.reflexive.assignment;

import logic.TestClass;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.reflexiveset.identity.SetIdentityFunction;
import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class ReflexiveAssignmentFactoryTest {
	private static ReflexiveAssignmentFactory<TestClass> factory;
	private ArrayList<Function<?, ?>> functions;
	private List<Token> tokens;
	private static SimpleLogicLexerImpl lexer;

	@BeforeClass
	public static void setUp() {
		lexer   = new SimpleLogicLexerImpl();
		factory = new ReflexiveAssignmentFactory<>();
	}

	@Test
	public void testCreateElement() throws Exception {
		ReflexiveAssignment<TestClass> expected = new ReflexiveAssignment<TestClass>(
				new IdentityFunction<>("x"),
				"x",
				new IdentityFunction<>("y")
		);

		setUpTokens("x where x is y");
		setUpFunctions("", "", "", "", "");
		ReflexiveAssignment<TestClass> actual = (ReflexiveAssignment<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);
	}

	private void setUpFunctions(String... identityFunctionParameters) {
		functions = new ArrayList<>();
		for (String identityFunctionParameter : identityFunctionParameters) {
			if (identityFunctionParameter == null || identityFunctionParameter.isEmpty()) {
				functions.add(null);
			} else {
				functions.add(new SetIdentityFunction<>(identityFunctionParameter));
			}
		}
	}

	private void setUpTokens(String tokenString) throws Exception {
		tokens = lexer.tokeniseString(tokenString);
	}
}
