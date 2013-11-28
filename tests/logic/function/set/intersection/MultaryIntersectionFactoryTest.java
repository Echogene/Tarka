package logic.function.set.intersection;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.factory.FactoryTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class MultaryIntersectionFactoryTest extends FactoryTest<TestClass, TestClassUniverse, MultaryIntersectionFactory<TestClass>> {

	public MultaryIntersectionFactoryTest() {
		super(new MultaryIntersectionFactory<>(TestClass.class), new TestClassUniverse());
		universe.put("x");
		universe.put("y");
		universe.put("z");
		universe.putSet("X", "x");
		universe.putSet("Y", "y");
		universe.putSet("Z", "z");
	}

	@Test
	public void testCreateElement() throws Exception {
		Intersection<TestClass> expected = BinaryIntersectionFactory.createElement("X", "Y");

		Intersection<TestClass> actual = (Intersection<TestClass>) reader.read("(⋂ X Y)");

		assertEquals(expected, actual);
	}

	@Test
	public void testCreateElementWithThreeElements() throws Exception {
		Intersection<TestClass> expected = BinaryIntersectionFactory.createElement("X", "Y", "Z");

		Intersection<TestClass> actual = (Intersection<TestClass>) reader.read("(⋂ X Y Z)");

		assertEquals(expected, actual);
	}
}
