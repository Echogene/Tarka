package util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static util.CollectionUtils.createSet;

/**
 * @author Steven Weston
 */
public class CollectionUtilsTest {

	@Test
	public void testIntersectMaps() throws Exception {
		Collection<Map<String, Set<String>>> maps = Arrays.asList(
				MapUtils.createMap(
						Arrays.asList("x", "y"),
						Arrays.asList(createSet("a", "b"), createSet("b", "c"))
				),
				MapUtils.createMap(
						Arrays.asList("x", "z"),
						Arrays.asList(createSet("a"), createSet("d"))
				)
		);
		Map<String, Set<String>> intersection = MapUtils.intersect(maps);
		assertTrue(intersection.containsKey("x"));
		assertEquals(1, intersection.keySet().size());
		assertEquals(1, intersection.get("x").size());
	}
}
