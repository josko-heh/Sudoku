package hr.tvz.sudoku.documentation;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static hr.tvz.sudoku.util.InformUser.showMessage;

public class DocumentationGenerator {

	private DocumentationGenerator() {}
	
	public static void generate() {
		Path start = Paths.get(System.getProperty("user.dir"));

		try (Stream<Path> stream = Files.walk(start);
			 Writer writer = new FileWriter("documentation.txt")) 
		{
			stream.filter(path -> {
				String fileName = path.getFileName().toString();
				return fileName.endsWith(".class") && !fileName.startsWith("module-info");
			}).forEach(path -> {
				List<String> pathElements = new ArrayList<>();
				path.forEach(p -> pathElements.add(p.toString()));

				try {
					writer.write(path.getFileName().toString() + "\n");
	
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
	
					Class myClass = Class.forName(packageName.substring(0, packageName.length() - 7));
					Constructor[] constructors = myClass.getConstructors();
	
					for (Constructor c : constructors) {
						writer.write("Constructor: " + c.getName() + "\n");
					}
				} catch (ClassNotFoundException | IOException e) {
					throw new RuntimeException(e);
				}
			});
		} catch (Exception e) {
			handleFail(e);
		}

		showMessage("Generate documentation", "Documentation generated successfully!", "");
	}

	private static void handleFail(Exception e) {
		e.printStackTrace();
		showMessage("Generate documentation", "Failed to generate documentation!", e.getMessage());
	}
}
