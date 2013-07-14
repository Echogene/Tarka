package maths.number.integer.model;

import logic.evaluable.Evaluable;
import logic.factory.SimpleLogicReaderImpl;
import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexiveset.ReflexiveSetFunction;
import maths.number.integer.Integer;
import maths.number.integer.model.universe.IntegerReader;
import maths.number.integer.model.universe.IntegerUniverse;
import reading.reading.ReadingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Steven Weston
 */
public class IntegerReaderRepl {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		IntegerUniverse universe = new IntegerUniverse();
		SimpleLogicReaderImpl<Integer> reader = IntegerReader.createStandardReader();
		String in = "";
		while (!in.equals("exit")) {
			in =  br.readLine();
			try {
				Function<?, ?> function = reader.read(in);
				if (function instanceof ReflexiveFunction) {
					ReflexiveFunction<Integer> reflexiveFunction = (ReflexiveFunction<Integer>) function;
					System.out.println(reflexiveFunction.evaluate(universe));
				} else if (function instanceof Evaluable) {
					Evaluable<Integer> evaluable = (Evaluable<Integer>) function;
					System.out.println(evaluable.evaluate(universe));
				} else if (function instanceof ReflexiveSetFunction) {
					ReflexiveSetFunction<Integer> setFunction = (ReflexiveSetFunction<Integer>) function;
					System.out.println(setFunction.evaluate(universe));
				}
			} catch (ReadingException e) {
				e.printStackTrace();
			} catch (ClassCastException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}