package logic.type;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.factory.SimpleLogicLexer;
import logic.factory.SimpleLogicParser;
import org.junit.Test;
import reading.lexing.Lexer;
import reading.lexing.LexerException;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import reading.parsing.Parser;
import reading.parsing.ParserException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;
import static org.junit.Assert.assertEquals;
import static util.NumberUtils.ordinal;

/**
 * @author Steven Weston
 */
public class SimpleLogicTypeInferrorTest {

	private final Lexer lexer;
	private final Parser parser;
	private final SimpleLogicTypeInferror<TestClass> inferror;
	private final TestClassUniverse universe;

	public SimpleLogicTypeInferrorTest() {
		lexer = new SimpleLogicLexer();
		parser = new SimpleLogicParser();
		universe = new TestClassUniverse();
		universe.put("2");
		inferror = new SimpleLogicTypeInferror<>(
				asList(
					(nodes, functionTypes) -> {
						Map<String, Type> output = new HashMap<>();
						boolean hasNest = nodes.get(1).getToken().isOfType(OPEN_BRACKET);
						Type type = functionTypes.getPassedValues().get(nodes.get(hasNest ? 6 : 5));
						if (type != null) {
							output.put(nodes.get(hasNest ? 4 : 3).getToken().getValue(), type);
						}
						return output;
					}
				),
				universe
		);
	}

	@Test
	public void testSimpleTypeInference() throws Exception {
		ParseTree tree = parse("(x where x is 2)");
		Map<ParseTreeNode, Type> map = inferror.inferTypes(tree, new HashMap());
		checkNodes(tree, map, asList(0, 1, 3, 5));
	}

	@Test
	public void testIgnoredVariableTypeInference() throws Exception {
		ParseTree tree = parse("(2 where x is 2)");
		Map<ParseTreeNode, Type> map = inferror.inferTypes(tree, new HashMap());
		checkNodes(tree, map, asList(0, 1, 3, 5));
	}

	@Test
	public void testHeadNestedTypeInference() throws Exception {
		ParseTree tree = parse("((y where y is x) where x is 2)");
		Map<ParseTreeNode, Type> map = inferror.inferTypes(tree, new HashMap());
		checkNodes(tree, map, asList(0, 1, 2, 4, 6, 9, 11));
	}

	@Test
	public void testHeadNestedTwiceTypeInference() throws Exception {
		ParseTree tree = parse("(((z where z is y) where y is x) where x is 2)");
		Map<ParseTreeNode, Type> map = inferror.inferTypes(tree, new HashMap());
		checkNodes(tree, map, asList(0, 1, 2, 3, 5, 7, 10, 12, 15 ,17));
	}

	@Test
	public void testTailNestedTypeInference() throws Exception {
		ParseTree tree = parse("(x where x is (y where y is 2))");
		Map<ParseTreeNode, Type> map = inferror.inferTypes(tree, new HashMap());
		checkNodes(tree, map, asList(0, 1, 3, 5, 6, 8, 10));
	}

	@Test
	public void testTailNestedTwiceTypeInference() throws Exception {
		ParseTree tree = parse("(x where x is (y where y is (z where z is 2)))");
		Map<ParseTreeNode, Type> map = inferror.inferTypes(tree, new HashMap());
		checkNodes(tree, map, asList(0, 1, 3, 5, 6, 8, 10, 11, 13, 15));
	}

	@Test
	public void testTailAndHeadNestedTypeInference() throws Exception {
		ParseTree tree = parse("((x where x is y) where y is (z where z is 2))");
		Map<ParseTreeNode, Type> map = inferror.inferTypes(tree, new HashMap());
		checkNodes(tree, map, asList(0, 1, 2, 4, 6, 9, 11, 12, 14, 16));
	}

	@Test
	public void testHeadThenTailNestedTypeInference() throws Exception {
		ParseTree tree = parse("((x where x is (y where y is z)) where z is 2)");
		Map<ParseTreeNode, Type> map = inferror.inferTypes(tree, new HashMap());
		checkNodes(tree, map, asList(0, 1, 2, 4, 6, 7, 9, 11, 15, 17));
	}

	@Test
	public void testTailThenHeadNestedTypeInference() throws Exception {
		ParseTree tree = parse("(x where x is ((z where z is y) where y is 2))");
		Map<ParseTreeNode, Type> map = inferror.inferTypes(tree, new HashMap());
		checkNodes(tree, map, asList(0, 1, 3, 5, 6, 7, 9, 11, 14, 16));
	}

	@Test
	public void testTailAndHeadNestedTwiceTypeInference() throws Exception {
		ParseTree tree = parse("(((a where a is b) where b is (c where c is d)) where d is ((e where e is f) where f is (g where g is 2)))");
		Map<ParseTreeNode, Type> map = inferror.inferTypes(tree, new HashMap());
		checkNodes(tree, map, asList(0, 1, 2, 3, 5, 7, 10, 12, 13, 15, 17, 21, 23, 24, 25, 27, 29, 32, 34, 35, 37, 39));
	}

	private void checkNodes(ParseTree tree, Map<ParseTreeNode, Type> map, List<Integer> notNull) {
		for (int index = 0; index < tree.getNodes().size(); index++) {
			Type type = map.get(tree.getNodes().get(index));
			if (notNull.contains(index)) {
				assertNotNull(ordinal(index) + " element should not be null", type);
				assertEquals(TestClass.class, type);
			} else {
				assertNull(ordinal(index) + " element should be null", type);
			}
		}
	}

	private ParseTree parse(String string) throws LexerException, ParserException {
		return parser.parseTokens(lexer.tokeniseString(string));
	}
}
