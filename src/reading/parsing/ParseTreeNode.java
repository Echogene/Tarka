package reading.parsing;

import javafx.util.Pair;
import reading.lexing.Token;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface ParseTreeNode {
	public ParseTreeNode getMother();
	public ParseTreeNode getFather();
	public List<ParseTreeNode> getChildren();
	public Token getToken();
	public ParseTreeNode getSpouse();
	public int getDepth();
	public List<ParseTreeNode> getDescendants();
	public List<ParseTreeNode> getMaternalAncestors();

	/**
	 * @return the parents of this node in the form (mother, father).
	 */
	public Pair<ParseTreeNode, ParseTreeNode> getParents();
}
