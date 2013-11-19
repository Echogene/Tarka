package logic.function.evaluable.predicate;

import javafx.util.Pair;
import logic.Nameable;
import logic.function.evaluable.EvaluableFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;

import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class PredicateFactory<T extends Nameable, F extends Predicate<T>> extends EvaluableFactory<T, F> {

	protected PredicateFactory(List<CheckerWithNumber> checkers, List<Pair<String, String>> acceptedBracketPairs) {
		super(checkers, acceptedBracketPairs);
	}
}
