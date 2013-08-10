package logic.function.reflexive.assignment;

import logic.TestClass;
import logic.factory.FactoryTest;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.reflexive.identity.IdentityFunctionFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class ReflexiveAssignmentFactoryTest extends FactoryTest<ReflexiveAssignmentFactory<TestClass>> {

	public ReflexiveAssignmentFactoryTest() {
		factory = new ReflexiveAssignmentFactory<>();
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
		ReflexiveAssignment<TestClass> expected = new ReflexiveAssignment<TestClass>(
				new IdentityFunction<>("x"),
				"x",
				new IdentityFunction<>("y")
		);

		setUpTokens("(x where x is y)");
		setUpFunctions();
		ReflexiveAssignment<TestClass> actual = (ReflexiveAssignment<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);
	}
}
