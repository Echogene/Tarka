package logic.type.match.constant;

import reading.parsing.ParseTreeNode;
import util.StringUtils;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author Steven Weston
 */
public class NoTypeException extends Exception {

	public NoTypeException(List<ParseTreeNode> nodes, ParseTreeNode argument) {

		super(MessageFormat.format(
				"The following argument had no possible type:\n{0}",
				StringUtils.identify(
						nodes,
						node -> node.equals(argument),
						Object::toString
				)
		));
	}
}
