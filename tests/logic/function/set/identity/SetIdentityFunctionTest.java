package logic.function.set.identity;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.identity.SetIdentityFunction;
import logic.set.Set;
import logic.set.finite.StandardSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class SetIdentityFunctionTest {
	@Test
	public void testEvaluate() throws Exception {
		TestClassUniverse universe = new TestClassUniverse();
		StandardSet<Set<TestClass>> sets = new StandardSet<>("sets");
		StandardSet<TestClass> X = new StandardSet<>("X");
		sets.put(X);

		universe.setUniversalSetOfSets(sets);

		SetIdentityFunction<TestClass> function = new SetIdentityFunction<>("X");
		assertEquals("Expect evaluation of function with variables to be equal to " + X.toString(), X, function.evaluate(universe));
	}
}
