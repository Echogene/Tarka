package logic.function.reflexive.identity;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.set.NamedSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class IdentityFunctionTest {
	@Test
	public void testEvaluate() throws Exception {
		NamedSet<TestClass> variables = new NamedSet<>("variables");
		TestClass x = new TestClass("x");
		variables.put(x);

		IdentityFunction<TestClass> function = new IdentityFunction<>("x");
		assertEquals("Expect evaluation of function to be equal to " + x.toString(), x, function.evaluate(variables));

		TestClassUniverse universe = new TestClassUniverse();
		universe.setVariables(variables);
		assertEquals("Expect evaluation of function with variables to be equal to " + x.toString(), x, function.evaluate(universe));

		universe = new TestClassUniverse();
		universe.setUniverse(variables);
		assertEquals("Expect evaluation of function with no variables to be equal to " + x.toString(), x, function.evaluate(universe));
	}
}
