package logic.function.reflexive.assignment;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.factory.SimpleLogicReaderImpl;
import logic.function.reflexive.ReflexiveFunction;
import logic.identity.IdentityFunction;
import logic.set.finite.StandardSet;
import maths.number.integer.Integer;
import maths.number.integer.model.universe.IntegerReader;
import maths.number.integer.model.universe.IntegerUniverse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class ReflexiveAssignmentTest {
	@Test
	public void testSimpleEvaluation() throws Exception {
		TestClassUniverse universe = new TestClassUniverse();
		StandardSet<TestClass> universalSet = new StandardSet<>("universalSet");
		universe.setUniversalSet(universalSet);

		universalSet.put(new TestClass("x"));
		universalSet.put(new TestClass("y"));

		ReflexiveAssignment<TestClass> assignment = new ReflexiveAssignment<TestClass>(
				new IdentityFunction<>("x"),
				"x",
				new IdentityFunction<>("y")
		);

		assertEquals(new TestClass("y"), assignment.evaluate(universe));
	}

	@Test
	public void testEvaluationWithAddition() throws Exception {
		IntegerUniverse universe = new IntegerUniverse();

		SimpleLogicReaderImpl<Integer> reader = IntegerReader.createStandardReader(universe);

		ReflexiveAssignment<Integer> assignment = new ReflexiveAssignment<>(
				(ReflexiveFunction<Integer>) reader.read("(2+x)"),
				"x",
				(ReflexiveFunction<Integer>) reader.read("(2+2)")
		);

		assertEquals(new Integer("6"), assignment.evaluate(universe));
	}
}
