package logic.factory;

import logic.StandardReader;
import logic.function.Function;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Steven Weston
 */
public class SimpleLogicReaderImplTest {
	private static SimpleLogicReaderImpl reader;

	@BeforeClass
	public static void setUp() {
		reader = StandardReader.createStandardReader();
	}

	@Test
	public void testRead() throws Exception {
		Function<?, ?> expected;
		Function<?, ?> actual;

		actual = reader.read("(∃!y(¬∀x((x∊X)∨(y∊(⋃ X Y Z)))))");
		System.out.println(actual);
	}
}
