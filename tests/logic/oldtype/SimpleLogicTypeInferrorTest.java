package logic.oldtype;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.function.assignment.AssignmentFactory;
import ophelia.util.MapUtils;
import org.junit.Test;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static ophelia.util.NumberUtils.ordinal;
import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class SimpleLogicTypeInferrorTest
		extends AbstractTypeInferrorTest<TestClass, TestClassUniverse, SimpleLogicTypeInferror<TestClass>> {

	private static final List<AssignmentFactory<TestClass>> ASSIGNMENT_FACTORY =
			singletonList(new AssignmentFactory<>(TestClass.class));

	public SimpleLogicTypeInferrorTest() {

		super(new TestClassUniverse());
		universe.put("2");
	}

	@Test
	public void testSimpleTypeInference() throws Exception {
		ParseTree tree = parse("(x where x is 2)");
		inferror = new SimpleLogicTypeInferror<>(universe, tree, createMap(tree, 0), createMap(tree, 0));
		Map<ParseTreeNode, Set<Type>> map = inferror.inferTypes();
		checkNodes(tree, map, asList(0, 1, 5));
	}

	@Test
	public void testIgnoredVariableTypeInference() throws Exception {
		ParseTree tree = parse("(2 where x is 2)");
		inferror = new SimpleLogicTypeInferror<>(universe, tree, createMap(tree, 0), createMap(tree, 0));
		Map<ParseTreeNode, Set<Type>> map = inferror.inferTypes();
		checkNodes(tree, map, asList(0, 1, 5));
	}

	@Test
	public void testHeadNestedTypeInference() throws Exception {
		ParseTree tree = parse("((y where y is x) where x is 2)");
		inferror = new SimpleLogicTypeInferror<>(universe, tree, createMap(tree, 0, 1), createMap(tree, 0, 1));
		Map<ParseTreeNode, Set<Type>> map = inferror.inferTypes();
		checkNodes(tree, map, asList(0, 1, 2, 6, 11));
	}

	@Test
	public void testHeadNestedTwiceTypeInference() throws Exception {
		ParseTree tree = parse("(((z where z is y) where y is x) where x is 2)");
		inferror = new SimpleLogicTypeInferror<>(universe, tree, createMap(tree, 0, 1, 2), createMap(tree, 0, 1, 2));
		Map<ParseTreeNode, Set<Type>> map = inferror.inferTypes();
		checkNodes(tree, map, asList(0, 1, 2, 3, 7, 12, 17));
	}

	@Test
	public void testTailNestedTypeInference() throws Exception {
		ParseTree tree = parse("(x where x is (y where y is 2))");
		inferror = new SimpleLogicTypeInferror<>(universe, tree, createMap(tree, 0, 5), createMap(tree, 0, 5));
		Map<ParseTreeNode, Set<Type>> map = inferror.inferTypes();
		checkNodes(tree, map, asList(0, 1, 5, 6, 10));
	}

	@Test
	public void testTailNestedTwiceTypeInference() throws Exception {
		ParseTree tree = parse("(x where x is (y where y is (z where z is 2)))");
		inferror = new SimpleLogicTypeInferror<>(universe, tree, createMap(tree, 0, 5, 10), createMap(tree, 0, 5, 10));
		Map<ParseTreeNode, Set<Type>> map = inferror.inferTypes();
		checkNodes(tree, map, asList(0, 1, 5, 6, 10, 11, 15));
	}

	@Test
	public void testTailAndHeadNestedTypeInference() throws Exception {
		ParseTree tree = parse("((x where x is y) where y is (z where z is 2))");
		inferror = new SimpleLogicTypeInferror<>(universe, tree, createMap(tree, 0, 1, 11), createMap(tree, 0, 1, 11));
		Map<ParseTreeNode, Set<Type>> map = inferror.inferTypes();
		checkNodes(tree, map, asList(0, 1, 2, 6, 11, 12, 16));
	}

	@Test
	public void testHeadThenTailNestedTypeInference() throws Exception {
		ParseTree tree = parse("((x where x is (y where y is z)) where z is 2)");
		inferror = new SimpleLogicTypeInferror<>(universe, tree, createMap(tree, 0, 1, 6), createMap(tree, 0, 1, 6));
		Map<ParseTreeNode, Set<Type>> map = inferror.inferTypes();
		checkNodes(tree, map, asList(0, 1, 2, 6, 7, 11, 17));
	}

	@Test
	public void testTailThenHeadNestedTypeInference() throws Exception {
		ParseTree tree = parse("(x where x is ((z where z is y) where y is 2))");
		inferror = new SimpleLogicTypeInferror<>(universe, tree, createMap(tree, 0, 5, 6), createMap(tree, 0, 5, 6));
		Map<ParseTreeNode, Set<Type>> map = inferror.inferTypes();
		checkNodes(tree, map, asList(0, 1, 5, 6, 7, 11, 16));
	}

	@Test
	public void testTailAndHeadNestedTwiceTypeInference() throws Exception {
		ParseTree tree = parse("(((a where a is b) where b is (c where c is d)) where d is ((e where e is f) where f is (g where g is 2)))");
		inferror = new SimpleLogicTypeInferror<>(universe, tree, createMap(tree, 0, 1, 2, 12, 23, 24, 34), createMap(tree, 0, 1, 2, 12, 23, 24, 34));
		Map<ParseTreeNode, Set<Type>> map = inferror.inferTypes();
		checkNodes(tree, map, asList(0, 1, 2, 3, 7, 12, 13, 17, 23, 24, 25, 29, 34, 35, 39));
	}

	private void checkNodes(ParseTree tree, Map<ParseTreeNode, Set<Type>> map, List<Integer> notNull) {
		for (int index = 0; index < tree.getNodes().size(); index++) {
			Set<Type> types = map.get(tree.getNodes().get(index));
			if (notNull.contains(index)) {
				assertNotNull(ordinal(index) + " element should not be null", types);
				assertEquals(Collections.<Type>singleton(TestClass.class), types);
			} else {
				assertNull(ordinal(index) + " element should be null", types);
			}
		}
	}

	private Map<ParseTreeNode, List<AssignmentFactory<TestClass>>> createMap(ParseTree tree, Integer... indices) {
		List<ParseTreeNode> nodes = new ArrayList<>(indices.length);
		for (Integer index : indices) {
			nodes.add(tree.getNodes().get(index));
		}
		return MapUtils.<ParseTreeNode, List<AssignmentFactory<TestClass>>>createMap(nodes, ASSIGNMENT_FACTORY);
	}
}
