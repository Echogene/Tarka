package logic.evaluable.constants;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.set.NamedSet;
import logic.set.Set;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class LogicalConstantTest {
	@Test
	public void testEvaluate() throws Exception {
		LogicalConstant<TestClass> tautology = new LogicalConstant<>(true);
		LogicalConstant<TestClass> contradiction = new LogicalConstant<>(false);
		Set<TestClass> emptySet = new NamedSet<>("∅");
		TestClassUniverse emptyUniverse = new TestClassUniverse();

		assertTrue("Expect tautology to evaluate to true", tautology.evaluate(emptySet));
		assertTrue("Expect tautology to evaluate to true", tautology.evaluate(emptyUniverse));

		assertFalse("Expect contradiction to evaluate to false", contradiction.evaluate(emptySet));
		assertFalse("Expect contradiction to evaluate to false", contradiction.evaluate(emptyUniverse));
	}
}
