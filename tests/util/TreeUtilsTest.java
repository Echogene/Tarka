package util;

import org.junit.Test;

import java.util.*;

import static java.util.function.Function.identity;

/**
 * @author Steven Weston
 */
public class TreeUtilsTest {

	@Test
	public void testPrettyPrint() throws Exception {
		Map<String, List<String>> parentToChild = new HashMap<>();
		parentToChild.put("a", Arrays.asList("b", "c"));
		parentToChild.put("b", Arrays.asList("d", "e"));
		parentToChild.put("c", Arrays.asList("f", "g"));
		parentToChild.put("d", Arrays.asList("h", "i"));
		parentToChild.put("e", Arrays.asList("j", "k"));
		parentToChild.put("f", Arrays.asList("l", "m"));
		parentToChild.put("g", Arrays.asList("n", "o"));
		parentToChild.put("h", Arrays.asList("p", "q"));
		parentToChild.put("i", Arrays.asList("r", "s"));
		parentToChild.put("j", Arrays.asList("t", "u"));
		parentToChild.put("k", Arrays.asList("w", "x"));
		parentToChild.put("l", Arrays.asList("y", "z"));
		parentToChild.put("m", Arrays.asList("α", "β"));
		parentToChild.put("n", Arrays.asList("γ", "δ"));
		parentToChild.put("o", Arrays.asList("ε", "ζ"));

		String prettyPrint = TreeUtils.prettyPrint(
				Collections.singletonList("a"),
				identity(),
				parentToChild::get,
				1
		);

//		System.out.print(prettyPrint);
	}
}
