package logic.type.match.state;

import logic.oldtype.map.MapWithErrors;
import logic.type.match.initial.InitialMatch;
import reading.parsing.ParseTreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Steven Weston
 */
public class TypeInferenceState {

	private final Map<ParseTreeNode, MapWithErrors<FunctionMatcher, ? extends InitialMatch>> matches = new HashMap<>();
}
