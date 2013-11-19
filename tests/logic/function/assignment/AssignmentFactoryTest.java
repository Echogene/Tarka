package logic.function.assignment;

import logic.TestClass;
import logic.factory.FactoryTest;
import logic.identity.IdentityFunctionFactory;
import logic.identity.MemberIdentityFunction;
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
		ReflexiveAssignment<TestClass> expected = new ReflexiveAssignment<TestClass>(
				new MemberIdentityFunction<>("x"),
				"x",
				new MemberIdentityFunction<>("y")
		);

		setUpTokens("(x where x is y)");
		setUpFunctions();
		ReflexiveAssignment<TestClass> actual = (ReflexiveAssignment<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);
	}
}
