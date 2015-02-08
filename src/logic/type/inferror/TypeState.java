package logic.type.inferror;

import reading.parsing.ParseTreeNode;
import util.MapUtils;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Steven Weston
 */
public class TypeState {

	private final Map<ParseTreeNode, Set<Type>> typeMap = new HashMap<>();

	public void refineTypes(Inferror inferror, ParseTreeNode node) {

		MapUtils.overlay(typeMap, node, inferror.inferTypes(node));
	}

	public Map<ParseTreeNode, Set<Type>> getTypeMap() {

		return Collections.unmodifiableMap(typeMap);
	}
}
