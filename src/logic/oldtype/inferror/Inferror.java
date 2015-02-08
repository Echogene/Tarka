package logic.oldtype.inferror;

import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author Steven Weston
 */
public interface Inferror {

	public Set<Type> inferTypes(ParseTreeNode node);
}
