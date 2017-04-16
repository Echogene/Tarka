package reading.parsing;

import reading.lexing.Token;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface ParseTreeNode {

	ParseTreeNode getMother();
	ParseTreeNode getFather();
	List<ParseTreeNode> getChildren();
	ParseTreeNode getSpouse();
	int getDepth();
	List<ParseTreeNode> getDescendants();
	List<ParseTreeNode> getMaternalAncestors();

	Token getToken();

	default String getValue() {
		return getToken().getValue();
	}
}
