package hr.tvz.sudoku.documentation;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static hr.tvz.sudoku.util.InformUser.showMessage;

public class DocumentationGenerator {

	private DocumentationGenerator() {}
	
	public static void generate() {
		Path start = Paths.get(System.getProperty("user.dir"));

		try (Stream<Path> stream = Files.walk(start);
			 Writer writer = new FileWriter("documentation.html")) 
		{
			writer.write(getHeader("Documentation", 1));
			
			stream.filter(path -> {
				String fileName = path.getFileName().toString();
				return fileName.endsWith(".class") && !fileName.startsWith("module-info");
			}).forEach(path -> {
				List<String> pathElements = new ArrayList<>();
				path.forEach(p -> pathElements.add(p.toString()));

				try {
					writer.write(getHtml(path.getFileName().toString(), "h3"));
	
					String packageName = "";
					boolean startPackage = false;
					for (String pathElement : pathElements) {
						if (startPackage) {
							packageName += pathElement + ".";
							continue;
						}
	
						if (pathElement.equals("classes"))
							startPackage = true;
					}
	
					Class clazz = Class.forName(packageName.substring(0, packageName.length() - 7));

					Class[] interfaces = clazz.getInterfaces();
					if (interfaces.length != 0) {
						writer.write(getHeader("Interfaces", 4));
						for (Class interfacee : interfaces) {
							writer.write(getHtml(interfacee.getName(), "span"));
						}
					}
					
					Class superclass = clazz.getSuperclass();
					if (superclass != null) {
						writer.write(getHeader("Superclass", 4));
						writer.write(getHtml(superclass.getName(), "span"));
					}

					List<Constructor> nonPrivateConstructors = Arrays.stream(clazz.getDeclaredConstructors())
							.filter(method -> !Modifier.isPrivate(method.getModifiers()))
							.toList();
					if (!nonPrivateConstructors.isEmpty())
						writer.write(getHeader("Constructors", 4));
					for (Constructor constructor : nonPrivateConstructors) {
						writer.write(getHtml(constructor.toGenericString(), "span"));
					}
					
					Annotation[] annotations = clazz.getDeclaredAnnotations();
					if (annotations.length != 0) {
						writer.write(getHeader("Annotations", 4));
						for (Annotation annotation : annotations) {
							writer.write(getHtml(annotation.toString(), "span"));
						}
					}

					List<Field> nonPrivateFields = Arrays.stream(clazz.getDeclaredFields())
							.filter(method -> !Modifier.isPrivate(method.getModifiers()))
							.toList();
					if (!nonPrivateFields.isEmpty())
						writer.write(getHeader("Fields", 4));
					for (Field field : nonPrivateFields) {
						writer.write(getHtml(field.toGenericString(), "span"));
					}
					
					List<Method> nonPrivateMethods = Arrays.stream(clazz.getDeclaredMethods())
							.filter(method -> !Modifier.isPrivate(method.getModifiers()))
							.toList();
					if (!nonPrivateMethods.isEmpty())
						writer.write(getHeader("Methods", 4));
					for (Method method : nonPrivateMethods) {
						writer.write(getHtml(method.toGenericString(), "span"));
					}

					writer.write("<hr>" + "\n");
				} catch (ClassNotFoundException | IOException e) {
					throw new RuntimeException(e);
				}
			});
		} catch (Exception e) {
			handleFail(e);
		}

		showMessage("Generate documentation", "Documentation generated successfully!", "");
	}

	private static String getHtml(String content, String element) {
		return "<" + element + ">" + content + "</" + element + "><br>\n";
	}
	
	private static String getHeader(String content, int headerLevel) {
		return "<h" + headerLevel + ">" + content + "</h" + headerLevel + ">\n";
	}

	private static void handleFail(Exception e) {
		e.printStackTrace();
		showMessage("Generate documentation", "Failed to generate documentation!", e.getMessage());
	}
}
