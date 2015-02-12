package logic.type.match.constant;

import logic.type.match.arguments.ArgumentedMatch;
import reading.parsing.ParseTreeNode;
import util.MapUtils;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Steven Weston
 */
public class ConstantsTypedMatch extends ArgumentedMatch {

	protected final Map<ParseTreeNode, Set<Type>> argumentTypeMap = new HashMap<>();

	protected ConstantsTypedMatch(
			List<ParseTreeNode> surroundedNodes,
			List<ParseTreeNode> arguments
	) {
		super(surroundedNodes, arguments);
	}

	public void typeArgument(ParseTreeNode argument, Set<Type> types) {

		assert arguments.contains(argument);

		MapUtils.overlay(argumentTypeMap, argument, types);
	}

	public Map<ParseTreeNode, Set<Type>> getArgumentTypes() {
		return Collections.unmodifiableMap(argumentTypeMap);
	}
}
