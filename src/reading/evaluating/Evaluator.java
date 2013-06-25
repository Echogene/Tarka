package reading.evaluating;

import reading.parsing.ParseTree;

/**
 * @author Steven Weston
 */
public interface Evaluator<T> {
	/**
	 * Create a {@code T} by evaluating a parse tree.
	 *
	 * @param tree the parse tree to evaluate
	 * @return a list of {@code T}s represented by the parse tree
	 */
	public T evaluate(ParseTree tree) throws EvaluatorException;
}
