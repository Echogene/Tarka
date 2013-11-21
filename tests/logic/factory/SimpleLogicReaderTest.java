package logic.factory;

import logic.StandardReader;
import logic.TestClass;
import logic.TestClassUniverse;
import logic.function.Function;
import logic.function.identity.MemberIdentityFunction;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class SimpleLogicReaderTest {
	private static SimpleLogicReader reader;
	private static TestClassUniverse universe;

	@BeforeClass
	public static void setUp() {
		universe = new TestClassUniverse();
		universe.put("x");
		reader = StandardReader.createStandardReader(universe);
	}

	@Test
	public void testCreateMemberIdentityFunction() throws Exception {
		Function<?, ?> expected;
		Function<?, ?> actual;

		expected = new MemberIdentityFunction<TestClass>("x");
		actual = reader.read("(x)");
		assertEquals(expected, actual);
	}

//	@Test
//	public void testRead() throws Exception {
//		Function<?, ?> expected;
//		Function<?, ?> actual;
//
//		actual = reader.read("(∃!y(¬∀x∊(V ∪ W)(((z where z is x)∊X)∨(y∊(⋃ X Y {a b c})))))");
//		System.out.println(actual);
//	}
}
