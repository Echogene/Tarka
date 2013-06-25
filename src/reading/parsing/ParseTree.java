package reading.parsing;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface ParseTree {
	public List<ParseTreeNode> getNodes();

	public ParseTree getSubtreeAt(ParseTreeNode node);

	public String getSubstringAt(ParseTreeNode node);
}
