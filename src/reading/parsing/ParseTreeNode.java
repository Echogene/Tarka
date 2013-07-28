package reading.parsing;

import reading.lexing.Token;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface ParseTreeNode {
	public ParseTreeNode getMother();
	public List<ParseTreeNode> getChildren();
	public Token getToken();
	public ParseTreeNode getSpouse();
	public int getDepth();
	public List<ParseTreeNode> getAllDescendants();
}
