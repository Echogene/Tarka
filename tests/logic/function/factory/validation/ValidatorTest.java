package logic.function.factory.validation;

import logic.factory.FactoryTest;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.group.TokenGroup;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class ValidatorTest extends FactoryTest<FunctionFactory<?, ?>> {
	@Test
	public void testGroupTokens() throws Exception {
		Validator validator = new Validator(null, null);
		List<TokenGroup> groups;

		setUpTokens("x () ∨ ∀");
		groups = validator.groupTokens(tokens);
		assertEquals(4, groups.size());
		assertTrue(groups.get(0).representsName());
		assertTrue(groups.get(1).representsFunction());
		assertTrue(groups.get(2).representsOperator());
		assertTrue(groups.get(3).representsQuantifier());

		setUpTokens("x [) ∨ ∀");
		groups = validator.groupTokens(tokens);
		assertEquals(4, groups.size());
		assertTrue(groups.get(0).representsName());
		assertTrue(groups.get(1).representsFunction());
		assertTrue(groups.get(2).representsOperator());
		assertTrue(groups.get(3).representsQuantifier());

		setUpTokens("x {} ∨ ∀");
		groups = validator.groupTokens(tokens);
		assertEquals(4, groups.size());
		assertTrue(groups.get(0).representsName());
		assertTrue(groups.get(1).representsFunction());
		assertTrue(groups.get(2).representsOperator());
		assertTrue(groups.get(3).representsQuantifier());
	}
}
