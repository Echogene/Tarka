package logic;

import logic.evaluable.constants.LogicalConstantFactory;
import logic.evaluable.predicate.EqualityPredicateFactory;
import logic.evaluable.predicate.MembershipPredicateFactory;
import logic.evaluable.statements.binary.BinaryStatementFactory;
import logic.evaluable.statements.quantified.QuantifiedStatementFactory;
import logic.evaluable.statements.unary.UnaryStatementFactory;
import logic.factory.SimpleLogicReaderImpl;
import logic.function.FunctionFactory;
import logic.function.reflexive.IdentityFunctionFactory;
import logic.function.reflexiveset.identity.SetIdentityFunctionFactory;
import logic.function.reflexiveset.union.UnionFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class StandardReader {
	public static <T extends Nameable> SimpleLogicReaderImpl<T> createStandardReader() {
		List<FunctionFactory<?, ?>> factories = Arrays.asList(
			new EqualityPredicateFactory<>(),
			new MembershipPredicateFactory<>(),
			new SetIdentityFunctionFactory<T>(),
			new IdentityFunctionFactory<T>(),
			new BinaryStatementFactory<>(),
			new LogicalConstantFactory<>(),
			new UnaryStatementFactory<>(),
			new QuantifiedStatementFactory<>(),
			new UnionFactory<>()
		);

		return new SimpleLogicReaderImpl<>(factories);
	}
}
