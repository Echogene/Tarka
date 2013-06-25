package logic.factory;

import logic.Nameable;
import logic.function.Function;
import logic.function.FunctionFactory;
import reading.reading.Reader;
import reading.reading.ReadingException;

import java.util.List;

/**
 * @author Steven Weston
 */
public class SimpleLogicReaderImpl<T extends Nameable> implements Reader<Function<?, ?>> {
	protected SimpleLogicLexerImpl lexer;
	protected SimpleLogicParserImpl parser;
	protected SimpleLogicEvaluatorImpl evaluator;

	public SimpleLogicReaderImpl(List<FunctionFactory<?, ?>> factories) {
		lexer = new SimpleLogicLexerImpl();
		parser = new SimpleLogicParserImpl();
		evaluator = new SimpleLogicEvaluatorImpl(factories);
	}

	@Override
	public Function<?, ?> read(String string) throws ReadingException {
		return evaluator.evaluate(parser.parseTokens(lexer.tokeniseString(string)));
	}
}
