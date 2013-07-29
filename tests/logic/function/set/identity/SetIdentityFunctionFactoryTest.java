package logic.function.set.identity;

import logic.TestClass;
import logic.factory.FactoryTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class SetIdentityFunctionFactoryTest extends FactoryTest<SetIdentityFunctionFactory<TestClass>> {
	public SetIdentityFunctionFactoryTest() {
		super();
		factory = new SetIdentityFunctionFactory<>();
		functionFactory = new SetIdentityFunctionFactory<>();
	}

	@Test
	public void testCurlyBracketsThrowException() throws Exception {
		setUpTokens("{Id X}");
		setUpFunctions("");
		expectFactoryException();
	}

	@Test
	public void testCreateElement() throws Exception {
		SetIdentityFunction<TestClass> expected;
		SetIdentityFunction<TestClass> actual;

		expected = new SetIdentityFunction<>("X");
		setUpTokens("(Id X)");
		actual = (SetIdentityFunction<TestClass>) factory.createElement(tokens);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);

		expected = new SetIdentityFunction<>(new SetIdentityFunction<>("X"));
		setUpTokens("(Id ())");
		setUpSetIdentityFunction("X");
		actual = (SetIdentityFunction<TestClass>) factory.createElement(tokens, functions);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);
	}

	@Test
	public void testMatchesStandardForm() throws Exception {
		setUpTokens("Id X");
		setUpSetIdentityFunction("");
		assertTrue("Expect standard tokens to match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("Id X Y");
		setUpSetIdentityFunction("");
		assertFalse("Expect additional parameter to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("f X");
		setUpSetIdentityFunction("");
		assertFalse("Expect bad function name to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("Id (");
		setUpSetIdentityFunction("");
		assertFalse("Expect bad parameter name to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("Id X");
		setUpSetIdentityFunction("X");
		assertFalse("Expect unexpected function to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("Id X");
		setUpSetIdentityFunction("");
		functions.add(null);
		assertFalse("Expect additional function to not match standard form", factory.matchesStandardForm(tokens, functions));
	}

	@Test
	public void testNoFunctions() throws Exception {
		setUpSetIdentityFunction("");
		assertTrue("Expect single null function to count as no function", factory.noFunctions(functions));

		functions = null;
		assertTrue("Expect no functions to count as no function", factory.noFunctions(functions));

		setUpSetIdentityFunction("X");
		assertFalse("Expect single function to not count as no function", factory.noFunctions(functions));
	}

	@Test
	public void testMatchesStandardFormWithFunction() throws Exception {
		setUpTokens("Id ()");
		setUpSetIdentityFunction("X");
		assertTrue("Expect standard form with function to match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));

		setUpTokens("Id ()");
		setUpSetIdentityFunction("");
		assertFalse("Expect missing function to not match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));

		setUpTokens("X ()");
		setUpSetIdentityFunction("X");
		assertFalse("Expect function name to not match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));

		setUpTokens("Id (X");
		setUpSetIdentityFunction("X");
		assertFalse("Expect bad brackets to not match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));

		setUpTokens("Id X)");
		setUpSetIdentityFunction("X");
		assertFalse("Expect bad brackets to not match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));
	}

	@Test
	public void testOneFunction() throws Exception {
		setUpSetIdentityFunction("");
		assertFalse("Expect single null function to not count as one function", factory.oneFunction(functions));

		functions = null;
		assertFalse("Expect no functions to not count as one function", factory.oneFunction(functions));

		setUpSetIdentityFunction("x");
		assertTrue("Expect single function to count as one function", factory.oneFunction(functions));

		setUpSetIdentityFunction("x");
		functions.add(null);
		assertFalse("Expect additional function to not count as one function", factory.oneFunction(functions));
	}
}
