package logic.function.reflexiveset.identity;

import logic.TestClass;
import logic.factory.FactoryTest;
import org.junit.Test;

import java.util.ArrayList;

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
	public void testCreateElement() throws Exception {
		SetIdentityFunction<TestClass> expected;
		SetIdentityFunction<TestClass> actual;

		expected = new SetIdentityFunction<>("X");
		setUpTokens("Id X");
		actual = (SetIdentityFunction<TestClass>) factory.createElement(tokens);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);

		expected = new SetIdentityFunction<>(new SetIdentityFunction<>("X"));
		setUpTokens("Id ()");
		setUpFunction("X");
		actual = (SetIdentityFunction<TestClass>) factory.createElement(tokens, functions);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);
	}

	@Test
	public void testMatchesStandardForm() throws Exception {
		setUpTokens("Id X");
		setUpFunction("");
		assertTrue("Expect standard tokens to match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("Id X Y");
		setUpFunction("");
		assertFalse("Expect additional parameter to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("f X");
		setUpFunction("");
		assertFalse("Expect bad function name to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("Id (");
		setUpFunction("");
		assertFalse("Expect bad parameter name to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("Id X");
		setUpFunction("X");
		assertFalse("Expect unexpected function to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("Id X");
		setUpFunction("");
		functions.add(null);
		assertFalse("Expect additional function to not match standard form", factory.matchesStandardForm(tokens, functions));
	}

	@Test
	public void testNoFunctions() throws Exception {
		setUpFunction("");
		assertTrue("Expect single null function to count as no function", factory.noFunctions(functions));

		functions = null;
		assertTrue("Expect no functions to count as no function", factory.noFunctions(functions));

		setUpFunction("X");
		assertFalse("Expect single function to not count as no function", factory.noFunctions(functions));
	}

	@Test
	public void testMatchesStandardFormWithFunction() throws Exception {
		setUpTokens("Id ()");
		setUpFunction("X");
		assertTrue("Expect standard form with function to match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));

		setUpTokens("Id ()");
		setUpFunction("");
		assertFalse("Expect missing function to not match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));

		setUpTokens("X ()");
		setUpFunction("X");
		assertFalse("Expect function name to not match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));

		setUpTokens("Id (X");
		setUpFunction("X");
		assertFalse("Expect bad brackets to not match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));

		setUpTokens("Id X)");
		setUpFunction("X");
		assertFalse("Expect bad brackets to not match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));
	}

	@Test
	public void testOneFunction() throws Exception {
		setUpFunction("");
		assertFalse("Expect single null function to not count as one function", factory.oneFunction(functions));

		functions = null;
		assertFalse("Expect no functions to not count as one function", factory.oneFunction(functions));

		setUpFunction("x");
		assertTrue("Expect single function to count as one function", factory.oneFunction(functions));

		setUpFunction("x");
		functions.add(null);
		assertFalse("Expect additional function to not count as one function", factory.oneFunction(functions));
	}

	private void setUpFunction(String identityFunctionParameter) {
		functions = new ArrayList<>(1);
		if (identityFunctionParameter == null || identityFunctionParameter.isEmpty()) {
			functions.add(null);
		} else {
			functions.add(new SetIdentityFunction<>(identityFunctionParameter));
		}
	}
}
