package logic.function.reflexive.assignment;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.factory.SimpleLogicReaderImpl;
import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunction;
import logic.set.NamedSet;
import logic.set.Set;
import maths.number.integer.Integer;
import maths.number.integer.universe.IntegerReader;
import maths.number.integer.universe.IntegerUniverse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class AssignmentTest {
	@Test
	public void testSimpleEvaluation() throws Exception {
		TestClassUniverse universe = new TestClassUniverse();
		Set<TestClass> universalSet = new NamedSet<>("universalSet");
		universe.setUniversalSet(universalSet);

		universalSet.put(new TestClass("x"));
		universalSet.put(new TestClass("y"));

		Assignment<TestClass> assignment = new Assignment<TestClass>(
				new IdentityFunction<>("x"),
				"x",
				new IdentityFunction<>("y")
		);

		assertEquals(new TestClass("y"), assignment.evaluate(universe));
	}

	@Test
	public void testEvaluationWithAddition() throws Exception {
		IntegerUniverse universe = new IntegerUniverse();

		SimpleLogicReaderImpl<Integer> reader = IntegerReader.createStandardReader();

		Assignment<Integer> assignment = new Assignment<>(
				(Function<Integer,?>) reader.read("(2+x)"),
				"x",
				(ReflexiveFunction<Integer>) reader.read("(2+2)")
		);

		assertEquals(new Integer("6"), assignment.evaluate(universe));
	}
}
