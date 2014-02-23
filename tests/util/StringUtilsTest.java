package util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class StringUtilsTest {

	@Test
	public void testIdentify() throws Exception {

		List<String> strings = Arrays.asList("a", "I", "at", "it", "bat", "lot", "bath", "rhythm", "atmosphere");
		System.out.println(
				StringUtils.identify(
						strings,
						s -> s.contains("a"),
						s -> s
				)
		);
	}
}
