package logic.factory;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import reading.reading.Reader;
import reading.reading.ReadingException;

import java.util.List;

/**
 * @author Steven Weston
 */
public class SimpleLogicReaderImpl<T extends Nameable> implements Reader<Function<?, ?>> {
	protected SimpleLogicLexer lexer;
	protected SimpleLogicParser parser;
	protected SimpleLogicEvaluator evaluator;

	public SimpleLogicReaderImpl(List<FunctionFactory<T, ?, ?>> factories) {
		lexer = new SimpleLogicLexer();
		parser = new SimpleLogicParser();
		evaluator = new SimpleLogicEvaluator<>(factories);
	}

	@Override
	public Function<?, ?> read(String string) throws ReadingException {
		return evaluator.evaluate(parser.parseTokens(lexer.tokeniseString(string)));
	}
}
