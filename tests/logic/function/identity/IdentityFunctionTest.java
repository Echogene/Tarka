package logic.function.identity;

import logic.TestClass;
import logic.TestClassModel;
import logic.TestClassUniverse;
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

		TestClassModel model = new TestClassModel();
		TestClassUniverse universe = model.getUniverse();
		universe.setVariables(variables);
		assertEquals("Expect evaluation of function with variables to be equal to " + x.toString(), x, function.evaluate(model));


		StandardSet<TestClass> universalSet = new StandardSet<>("universe");
		universalSet.put(x);
		universe = new TestClassUniverse();
		universe.setUniverse(universalSet);
		assertEquals("Expect evaluation of function with no variables to be equal to " + x.toString(), x, function.evaluate(model));
	}
}
