package reading.parsing;


import ophelia.util.ListUtils;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface ParseTree {
	List<ParseTreeNode> getNodes();

	default ParseTreeNode getFirstNode() {
		return ListUtils.first(getNodes());
	}

	default ParseTreeNode getNode(int index) {
		return getNodes().get(index);
	}

	ParseTree getSubtreeAt(ParseTreeNode node);

	String getSubstringAt(ParseTreeNode node);
}
