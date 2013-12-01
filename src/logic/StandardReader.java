package logic;

import logic.factory.SimpleLogicReader;
import logic.function.assignment.AssignmentFactory;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.evaluable.predicate.membership.MembershipPredicateFactory;
import logic.function.evaluable.statements.binary.BinaryStatementFactory;
import logic.function.evaluable.statements.quantified.restricted.RestrictedQuantifiedStatementFactory;
import logic.function.evaluable.statements.quantified.standard.QuantifiedStatementFactory;
import logic.function.evaluable.statements.unary.UnaryStatementFactory;
import logic.function.factory.FunctionFactory;
import logic.function.identity.IdentityFunctionFactory;
import logic.function.ifelse.IfElseFactory;
import logic.function.set.complex.ComplexSetFactory;
import logic.function.set.intersection.BinaryIntersectionFactory;
import logic.function.set.intersection.MultaryIntersectionFactory;
import logic.function.set.simple.SimpleSetFactory;
import logic.function.set.union.BinaryUnionFactory;
import logic.function.set.union.MultaryUnionFactory;
import logic.function.voidfunction.definition.member.DefinitionFactory;
import logic.model.universe.Universe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
public class StandardReader {
	public static <T extends Nameable> SimpleLogicReader<T> createStandardReader(Universe<T> universe) {
		List<FunctionFactory<T, ?, ?>> factories = getStandardFunctionFactories(universe.getTypeOfUniverse());
		return new SimpleLogicReader<>(factories, universe);
	}

	public static <T extends Nameable> List<FunctionFactory<T, ?, ?>> getStandardFunctionFactories(Class<T> universeType) {
		List<FunctionFactory<T, ?, ?>> output = new ArrayList<>();
		output.add(new IdentityFunctionFactory<>(universeType));
		output.add(new EqualityPredicateFactory<>(universeType));
		output.add(new MembershipPredicateFactory<>(universeType));
		output.add(new BinaryStatementFactory<>(universeType));
		output.add(new UnaryStatementFactory<>(universeType));
		output.add(new QuantifiedStatementFactory<>(universeType));
		output.add(new BinaryUnionFactory<>(universeType));
		output.add(new MultaryUnionFactory<>(universeType));
		output.add(new BinaryIntersectionFactory<>(universeType));
		output.add(new MultaryIntersectionFactory<>(universeType));
		output.add(new AssignmentFactory<>(universeType));
		output.add(new RestrictedQuantifiedStatementFactory<>(universeType));
		output.add(new DefinitionFactory<>(universeType));
		output.add(new SimpleSetFactory<>(universeType));
		output.add(new IfElseFactory<>(universeType));
		output.add(new ComplexSetFactory<>(universeType));
		return output;
	}
}
