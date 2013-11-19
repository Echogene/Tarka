package logic.function.reflexive.identity;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.identity.MemberIdentityFunction;
import logic.set.finite.StandardSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class IdentityFunctionTest {
	@Test
	public void testEvaluate() throws Exception {
		StandardSet<Object> variables = new StandardSet<>("variables");
		TestClass x = new TestClass("x");
		variables.put(x);

		MemberIdentityFunction<TestClass> function = new MemberIdentityFunction<>("x");

		TestClassUniverse universe = new TestClassUniverse();
		universe.setVariables(variables);
		assertEquals("Expect evaluation of function with variables to be equal to " + x.toString(), x, function.evaluate(universe));


		StandardSet<TestClass> universalSet = new StandardSet<>("universe");
		universalSet.put(x);
		universe = new TestClassUniverse();
		universe.setUniverse(universalSet);
		assertEquals("Expect evaluation of function with no variables to be equal to " + x.toString(), x, function.evaluate(universe));
	}
}
