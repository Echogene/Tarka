package logic.type.match.subfunction;

import logic.oldtype.map.MapWithErrors;
import logic.type.match.codomain.CodomainTypedMatch;
import logic.type.match.codomain.CodomainTyper;
import reading.parsing.ParseTreeNode;

import java.util.Map;

/**
 * @author Steven Weston
 */
public interface SubFunctionTyper {

	SubFunctionsTypedMatch addTypesOfSubFunctions(
			CodomainTypedMatch match,
			Map<ParseTreeNode, MapWithErrors<CodomainTyper, CodomainTypedMatch>> potentialMatches
	) throws SubFunctionMatcherException;
}
