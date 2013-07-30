package logic.function.set.identity;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.set.FiniteSet;
import logic.set.ModifiableSet;
import logic.set.Set;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class SetIdentityFunctionTest {
	@Test
	public void testEvaluate() throws Exception {
		TestClassUniverse universe = new TestClassUniverse();
		ModifiableSet<Set<TestClass>> sets = new FiniteSet<>("sets");
		FiniteSet<TestClass> X = new FiniteSet<>("X");
		sets.put(X);

		universe.setUniversalSetOfSets(sets);

		SetIdentityFunction<TestClass> function = new SetIdentityFunction<>("X");
		assertEquals("Expect evaluation of function with variables to be equal to " + X.toString(), X, function.evaluate(universe));
	}
}
