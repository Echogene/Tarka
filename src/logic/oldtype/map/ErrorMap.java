package logic.oldtype.map;

import java.util.Collection;

/**
 * @author Steven Weston
 */
public interface ErrorMap {

	boolean allFailed();

	boolean allPassed();

	String concatenateErrorMessages();

	Collection<String> getErrorMessages();
}
