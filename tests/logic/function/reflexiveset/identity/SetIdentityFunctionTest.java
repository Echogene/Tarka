package logic.function.reflexiveset.identity;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.set.NamedSet;
import logic.set.Set;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class SetIdentityFunctionTest {
	@Test
	public void testEvaluate() throws Exception {
		Set<Set<TestClass>> sets = new NamedSet<>("sets");
		NamedSet<TestClass> X = new NamedSet<>("X");
		sets.put(X);

		SetIdentityFunction<TestClass> function = new SetIdentityFunction<>("X");
		assertEquals("Expect evaluation of function to be equal to " + X.toString(), X, function.evaluate(sets));

		TestClassUniverse universe = new TestClassUniverse();
		universe.setUniversalSetOfSets(sets);
		assertEquals("Expect evaluation of function with variables to be equal to " + X.toString(), X, function.evaluate(universe));
	}
}
