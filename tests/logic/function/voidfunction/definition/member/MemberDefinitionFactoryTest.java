package logic.function.voidfunction.definition.member;

import logic.factory.FactoryTest;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.reflexive.identity.IdentityFunctionFactory;
import maths.number.integer.Integer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class MemberDefinitionFactoryTest extends FactoryTest<MemberDefinitionFactory<Integer>> {

	public MemberDefinitionFactoryTest() {
		factory = new MemberDefinitionFactory<>();
		functionFactory = new IdentityFunctionFactory<Integer>();
	}

	@Test
	public void testCreateElement() throws Exception {
		MemberDefinition<Integer> expected;
		MemberDefinition<Integer> actual;

		setUpTokens("(x ≔ 2)");
		setUpFunctions("", "");

		expected = new MemberDefinition<>("x", new IdentityFunction<>("2"));
		actual = (MemberDefinition<Integer>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("(x ≔ ())");
		setUpFunctions("", "(2)");

		expected = new MemberDefinition<>("x", new IdentityFunction<>("2"));
		actual = (MemberDefinition<Integer>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);
	}
}