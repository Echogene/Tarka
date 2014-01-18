package logic.factory;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.model.universe.Universe;
import logic.type.TypeInferrorException;
import reading.reading.Reader;
import reading.reading.ReadingException;

import java.util.List;

/**
 * @author Steven Weston
 */
public class SimpleLogicReader<T extends Nameable> implements Reader<Function<T, ?, ?>> {

	private final SimpleLogicLexer lexer;
	private final SimpleLogicParser parser;
	private final SimpleLogicEvaluator<T> evaluator;

	public SimpleLogicReader(List<FunctionFactory<T, ?, ?>> factories, Universe<T, ?, ?> universe) {
		lexer = new SimpleLogicLexer();
		parser = new SimpleLogicParser();
		evaluator = new SimpleLogicEvaluator<>(factories, universe);
	}

	@Override
	public <F extends Function<T, ?, ?>> F read(String string) throws ReadingException, TypeInferrorException {
		return (F) evaluator.evaluate(parser.parseTokens(lexer.tokeniseString(string)));
	}

	public void addFactory(FunctionFactory<T, ?, ?> factory) {
		evaluator.addFactory(factory);
	}
}
