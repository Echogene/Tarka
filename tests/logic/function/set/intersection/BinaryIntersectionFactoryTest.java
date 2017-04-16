package logic.function.set.intersection;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.factory.FactoryTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class BinaryIntersectionFactoryTest extends FactoryTest<TestClass, TestClassUniverse, BinaryIntersectionFactory<TestClass>>{

	public BinaryIntersectionFactoryTest() {
		super(new BinaryIntersectionFactory<>(TestClass.class), new TestClassUniverse());
		universe.put("x");
		universe.put("y");
		universe.putSet("X", "x");
		universe.putSet("Y", "y");
	}

	@Test
	public void testCreateElement() throws Exception {
		Intersection<TestClass> expected = BinaryIntersectionFactory.createElement("X", "Y");

		Intersection<TestClass> actual = reader.read("(X ∩ Y)");

		assertEquals(expected, actual);
	}
}
