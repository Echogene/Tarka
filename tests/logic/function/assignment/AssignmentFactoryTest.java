package logic.function.assignment;

import logic.TestClass;
import logic.factory.FactoryTest;
import logic.function.identity.IdentityFunctionFactory;
import logic.function.identity.MemberIdentityFunction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class AssignmentFactoryTest extends FactoryTest<AssignmentFactory<TestClass>> {

	public AssignmentFactoryTest() {
		factory = new AssignmentFactory<>(TestClass.class);
		functionFactory = new IdentityFunctionFactory<>(TestClass.class);
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
		Assignment<TestClass, TestClass> actual = (Assignment<TestClass, TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);
	}
}
