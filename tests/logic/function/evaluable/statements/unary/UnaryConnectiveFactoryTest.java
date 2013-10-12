package logic.function.evaluable.statements.unary;

import logic.Connective;
import logic.factory.SimpleLogicLexer;
import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Token;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class UnaryConnectiveFactoryTest {
	private static List<Token> tokens;
	private static SimpleLogicLexer lexer;
	private static UnaryConnectiveFactory factory;

	@BeforeClass
	public static void setUp() {
		lexer   = new SimpleLogicLexer();
		factory = new UnaryConnectiveFactory();
	}

	@Test
	public void testCreateElements() throws Exception {
		Connective expected;
		Connective actual;

		expected = new UnaryConnective(UnaryConnective.UnaryConnectiveType.NEGATION);
		setUpTokens("¬");
		actual = factory.createElement(tokens);
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);

		expected = new UnaryConnective(UnaryConnective.UnaryConnectiveType.NEGATION);
		actual = factory.createElement("¬");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);

		expected = new UnaryConnective(UnaryConnective.UnaryConnectiveType.EMPTY);
		actual = factory.createElement("");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);
	}

	@Test
	public void testMatchesTokens() throws Exception {
		setUpTokens("¬");
		assertTrue("Expect standard token to match", factory.matchesTokens(tokens));

		tokens = null;
		assertFalse("Expect null tokens to not match", factory.matchesTokens(tokens));

		setUpTokens("¬ ¬");
		assertFalse("Expect wrong number of tokens to not match", factory.matchesTokens(tokens));

		setUpTokens("x");
		assertFalse("Expect bad token to not match", factory.matchesTokens(tokens));

		setUpTokens("∨");
		assertFalse("Expect wrong token to not match", factory.matchesTokens(tokens));
	}

	private void setUpTokens(String tokenString) throws Exception {
		tokens = lexer.tokeniseString(tokenString);
	}
}
