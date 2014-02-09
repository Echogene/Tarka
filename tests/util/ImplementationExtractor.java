package util;

import logic.function.Function;
import logic.function.reference.AbstractFunctionReference;
import org.junit.Test;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.text.MessageFormat.format;
import static org.junit.Assert.fail;
import static util.StringUtils.count;
import static util.StringUtils.substring;

/**
 * @author Steven Weston
 */
public class ImplementationExtractor {

	/**
	 * Search through the source files to find the implementation of a method.  This is quite naïve and probably doesn't
	 * support crazy stuff like inner classes.  It's suited to the formatting of my code, so probably isn't generic.
	 *
	 * @param sourceFileForClass
	 * @param method
	 * @return
	 */
	public static String extractImplementation(Path sourceFileForClass, Method method) throws IOException {
		String privacy = getPrivacy(method);
		String methodName = method.getName();
		Pattern pattern = Pattern.compile("\\s*" + privacy + ".*" + methodName + "\\(.*");
		StringBuilder sb = new StringBuilder();
		Map<String, String> matches = new HashMap<>();
		try (BufferedReader reader = Files.newBufferedReader(sourceFileForClass)) {
			String line;
			boolean matched = false;
			boolean functionOpened = false;
			String parameters = "";
			int bracketCount = 0;
			int lineNumber = 0;
			while ((line = reader.readLine()) != null) {
				lineNumber++;
				int openBracketCount = count("{", line);
				int closeBracketCount = count("}", line);
				if (pattern.matcher(line).matches()) {
					sb.append(lineNumber);
					matched = true;
				}
				if (matched) {
					sb.append(line).append("\n");
					if (!functionOpened) {
						parameters += substring(line, line.indexOf("(") + 1, line.indexOf(")"));
					}
					if (openBracketCount > 0) {
						functionOpened = true;
					}
					bracketCount += openBracketCount - closeBracketCount;
					if (bracketCount == 0 && functionOpened) {
						matched = false;
						functionOpened = false;
						matches.put(parameters, sb.toString());
						sb = new StringBuilder();
						parameters = "";
					}
				}
			}
		}
		for (Map.Entry<String, String> entry : matches.entrySet()) {
			if (methodParametersMatch(method, entry.getKey())) {
				return entry.getValue();
			}
		}
		return "";
	}

	private static boolean methodParametersMatch(Method method, String parameterString) {
		List<String> strings = splitParameters(parameterString);
		Type[] parameterTypes = method.getGenericParameterTypes();
		if (parameterTypes.length != strings.size()) {
			return false;
		}
		int i = 0;
		for (String string : strings) {
			Type type = parameterTypes[i];
			String parameterTypeFromString = substring(string, 0, string.lastIndexOf(" "));
			String parameterType = type.getTypeName();
			String simpleParameterType = parameterType.substring(parameterType.lastIndexOf(".") + 1);
			if (!parameterTypeFromString.equals(simpleParameterType)) {
				return false;
			}
			i++;
		}
		return true;
	}

	private static List<String> splitParameters(String parameterString) {
		List<String> output = new ArrayList<>();
		String tempString = parameterString;
		String parameter;
		int index;
		while (!tempString.isEmpty()) {
			index = tempString.indexOf(",");
			parameter = substring(tempString, 0, index);
			tempString = index == -1 ? "" : tempString.substring(index + 1);
			if (parameter.contains("<")
					&& !parameter.contains(">")
					&& (!tempString.contains("<")
						|| tempString.indexOf(">") < tempString.indexOf("<"))) {

				index = tempString.indexOf(",", tempString.indexOf(">"));
				parameter += "," + substring(tempString, 0, index);
				tempString = index == -1 ? "" : tempString.substring(index + 1);
			}
			output.add(parameter.trim());
		}
		return output;
	}

	private static String getPrivacy(Method method) {
		String privacy;
		if (Modifier.isPublic(method.getModifiers())) {
			privacy = "public";
		} else if (Modifier.isProtected(method.getModifiers())) {
			privacy = "protected";
		} else if (Modifier.isPrivate(method.getModifiers())) {
			privacy = "private";
		} else {
			privacy = "";
		}
		return privacy;
	}

	private static Path getSourceFileForClass(Class clazz) throws IOException {
		String currentDirectoryName = System.getProperty("user.dir");
		Path currentDirectory = Paths.get(currentDirectoryName);
		String fileName = clazz.getSimpleName() + ".java";

		final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + "**/" + fileName);

		final List<Path> matchedPaths = new ArrayList<>();
		Files.walkFileTree(currentDirectory, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if (file.getFileName() != null && matcher.matches(file)) {
					matchedPaths.add(file);
				}
				return CONTINUE;
			}
		});
		if (matchedPaths.size() == 1) {
			return matchedPaths.get(0);
		} else {
			throw new IOException(format("Cannot find unique file for class {0}", clazz.getSimpleName()));
		}
	}

	@Test
	public void test() throws Exception {
		Reflections logic = new Reflections("logic.function");

		checkImplementations(logic);
	}

	private void checkImplementations(Reflections reflections) throws NoSuchMethodException, IOException, NoSuchFieldException {
		Class<Function> baseClazz = Function.class;

		SortedSet<Class<? extends Function>> classes = new TreeSet<>((o1, o2) -> o1.getSimpleName().compareTo(o2.getSimpleName()));
		classes.addAll(reflections.getSubTypesOf(baseClazz));
		for (Class<?> clazz : classes) {
			if (Modifier.isInterface(clazz.getModifiers())) {
				continue;
			}
			Method method = clazz.getMethod("copy");
			if (Modifier.isAbstract(method.getModifiers())) {
				continue;
			}
			Path sourceFileForClass = getSourceFileForClass(clazz);
			String implementation = extractImplementation(sourceFileForClass, method);

			String[] split = implementation.split("\n");
			Pattern pattern = Pattern.compile("\\s*return new [^\\(]+\\((.*)\\);");
			String[] parameters = null;
			for (String s : split) {
				Matcher matcher = pattern.matcher(s);
				if (matcher.matches()) {
					parameters = matcher.group(1).split(", ");
				}
			}
			assert parameters != null;

			Pattern functionCopyPattern = Pattern.compile("(\\w+).copy\\(\\)");
			Pattern collectionCopyPattern = Pattern.compile("\\w*\\.?copy\\((.+)\\)");
			for (String parameter : parameters) {
				Matcher functionCopyMatcher = functionCopyPattern.matcher(parameter);
				Matcher collectionCopyMatcher = collectionCopyPattern.matcher(parameter);
				if (functionCopyMatcher.matches()) {
				} else if (collectionCopyMatcher.matches()) {
				} else {
					checkNonCopiedField(clazz, sourceFileForClass, implementation, parameter);
				}
			}
		}
	}

	private static final Map<Class, List<String>> IGNORED_FIELDS = new HashMap<>();
	static {
		IGNORED_FIELDS.put(AbstractFunctionReference.class, Arrays.asList("referee"));
	}

	private void checkNonCopiedField(Class<?> clazz, Path sourceFileForClass, String implementation, String parameter) throws NoSuchFieldException {
		Field field = getDeclaredFieldFromSuperClassesUntilFunction(clazz, parameter);
		if (IGNORED_FIELDS.containsKey(field.getDeclaringClass())) {
			if (IGNORED_FIELDS.get(field.getDeclaringClass()).contains(field.getName())) {
				return;
			}
		}
		if (Function.class.isAssignableFrom(field.getType())) {
			failFieldForNotBeingDeepCopied(clazz, sourceFileForClass, implementation, parameter);
		} else if (Collection.class.isAssignableFrom(field.getType())) {
			failFieldForNotBeingDeepCopied(clazz, sourceFileForClass, implementation, parameter);
		} else if (Map.class.isAssignableFrom(field.getType())) {
			ParameterizedType genericType = (ParameterizedType) field.getGenericType();
			Type valueType = genericType.getActualTypeArguments()[1];
			if (valueType instanceof ParameterizedType
					&& Function.class.isAssignableFrom((Class<?>) ((ParameterizedType) valueType).getRawType())) {
				failFieldForNotBeingDeepCopied(clazz, sourceFileForClass, implementation, parameter);
			}
		}
	}

	private void failFieldForNotBeingDeepCopied(Class<?> clazz, Path sourceFileForClass, String implementation, String parameter) {
		fail(format(
				"{0} ({1}:{2})\nThe field {3} in {4} was not deep copied",
				clazz.getName(),
				sourceFileForClass.getFileName(),
				implementation.substring(0, implementation.indexOf("\t")),
				parameter,
				clazz.getSimpleName()
		));
	}

	public static Field getDeclaredFieldFromSuperClassesUntilFunction(Class<?> clazz, String fieldName) throws NoSuchFieldException {
		if (Function.class.equals(clazz) || clazz == null) {
			throw new NoSuchFieldException(fieldName);
		}
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			return getDeclaredFieldFromSuperClassesUntilFunction(clazz.getSuperclass(), fieldName);
		}
	}
}
