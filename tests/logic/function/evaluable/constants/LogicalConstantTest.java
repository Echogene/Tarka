package logic.function.evaluable.constants;

import logic.TestClass;
import logic.TestClassUniverse;
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
		TestClassUniverse emptyUniverse = new TestClassUniverse();

		assertTrue("Expect tautology to evaluate to true", tautology.evaluate(emptyUniverse));

		assertFalse("Expect contradiction to evaluate to false", contradiction.evaluate(emptyUniverse));
	}
}
