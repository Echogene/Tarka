package logic.factory;

import logic.StandardReader;
import logic.TestClass;
import logic.function.Function;
import logic.model.universe.empty.EmptyUniverse;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Steven Weston
 */
public class SimpleLogicReaderTest {
	private static SimpleLogicReader reader;

	@BeforeClass
	public static void setUp() {
		reader = StandardReader.createStandardReader(new EmptyUniverse<>(TestClass.class));
	}

	@Test
	public void testRead() throws Exception {
		Function<?, ?> expected;
		Function<?, ?> actual;

		actual = reader.read("(∃!y(¬∀x∊(V ∪ W)(((z where z is x)∊X)∨(y∊(⋃ X Y {a b c})))))");
		System.out.println(actual);
	}
}
