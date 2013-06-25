package logic.factory;

import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Token;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class SimpleLogicParserImplTest {
	private static SimpleLogicLexerImpl lexer;
	private static List<Token> tokens;
	private static SimpleLogicParserImpl parser;
	private static ParseTree tree;

	@BeforeClass
	public static void setUp() {
		lexer = new SimpleLogicLexerImpl();
		parser = new SimpleLogicParserImpl();
	}

	@Test
	public void testParseTokens() throws Exception {
		testDepths("(x∊X)", new int[] {
				0, 1, 1, 1, 0
		}, "expected a simple statement to be parsed correctly");

		testDepths("((∃x(((f x y)=z)∨((g y z)∊Z)))∧(x=t))", new int[] {
				0, 1, 2, 2, 2, 3, 4, 5, 5, 5, 4, 4, 4, 3, 3, 3, 4, 5, 5, 5, 4, 4, 4, 3, 2, 1, 1, 1, 2, 2, 2, 1, 0
		}, "expected a complicated statement to be parsed correctly");
	}

	public static void testDepths(String test, int [] expectedDepths, String assertion) throws Exception {
		tokens = lexer.tokeniseString(test);
		tree = parser.parseTokens(tokens);
		int i = 0;
		for(ParseTreeNode n : tree.getNodes()) {
			int d = expectedDepths[i];
			assertEquals(assertion + " at index " + i, d, n.getDepth());
			i++;
		}
		assertEquals(assertion, expectedDepths.length, i);
	}
}
