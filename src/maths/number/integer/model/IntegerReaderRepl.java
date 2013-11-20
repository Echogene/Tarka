package maths.number.integer.model;

import logic.factory.SimpleLogicReader;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.function.voidfunction.VoidFunction;
import maths.number.integer.Integer;
import maths.number.integer.model.universe.IntegerReader;
import maths.number.integer.model.universe.IntegerUniverse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Steven Weston
 */
public class IntegerReaderRepl {

	private static IntegerUniverse universe;
	private static SimpleLogicReader<Integer> reader;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		universe = new IntegerUniverse();
		reader = IntegerReader.createStandardReader(universe);
		String in;
		while (true) {
			in =  br.readLine();
			if ("exit".equals(in)) {
				return;
			}
			if (in.isEmpty()) {
				continue;
			}
			evaluateInput(in);
		}
	}

	private static void evaluateInput(String in) {
		try {
			Function<?, ?> function = reader.read(in);
			if (function instanceof ReflexiveFunction) {
				ReflexiveFunction<Integer> reflexiveFunction = (ReflexiveFunction<Integer>) function;
				System.out.println(reflexiveFunction.evaluate(universe));
			} else if (function instanceof Evaluable) {
				Evaluable<Integer> evaluable = (Evaluable<Integer>) function;
				System.out.println(evaluable.evaluate(universe));
			} else if (function instanceof SetFunction) {
				SetFunction<Integer> setFunction = (SetFunction<Integer>) function;
				System.out.println(setFunction.evaluate(universe));
			} else if (function instanceof VoidFunction) {
				VoidFunction<Integer> voidFunction = (VoidFunction<Integer>) function;
				voidFunction.evaluate(universe);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
