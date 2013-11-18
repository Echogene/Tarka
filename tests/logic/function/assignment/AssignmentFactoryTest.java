package logic.function.assignment;

import logic.TestClass;
import logic.factory.FactoryTest;
import logic.identity.IdentityFunction;
import logic.identity.IdentityFunctionFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class AssignmentFactoryTest extends FactoryTest<AssignmentFactory<TestClass>> {

	public AssignmentFactoryTest() {
		factory = new AssignmentFactory<>();
		functionFactory = new IdentityFunctionFactory<TestClass>();
	}

	@Test
	public void testCurlyBracketsThrowException() throws Exception {
		setUpTokens("{x where x is y}");
		setUpFunctions();
		expectFactoryException();
	}

	@Test
	public void testCreateElement() throws Exception {
		Assignment<TestClass> expected = new Assignment<TestClass>(
				new IdentityFunction<>("x"),
				"x",
				new IdentityFunction<>("y")
		);

		setUpTokens("(x where x is y)");
		setUpFunctions();
		Assignment<TestClass> actual = (Assignment<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);
	}
}
