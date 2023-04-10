package hr.tvz.sudoku.components.main;

import hr.tvz.sudoku.components.board.Generator;
import hr.tvz.sudoku.control.SaveHandler;
import hr.tvz.sudoku.documentation.DocumentationGenerator;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class GameContainer {
	
	private Generator generator;

	public GameContainer(int size, int emptyBoxes) {
		this.generator = new Generator(size, emptyBoxes);
	}

	public Pane generate() {
		BorderPane pane = new BorderPane();

		Button saveButton = new Button("Save");
		saveButton.setOnAction(event -> SaveHandler.save(generator.getState()));
		Button loadButton = new Button("Load");
		loadButton.setOnAction(event -> SaveHandler.load().ifPresent(loadedGenerator -> {
			generator = loadedGenerator;
			pane.setCenter(generator.getBoard());
		}));
		Button docButton = new Button("Documentation");
		docButton.setOnAction(event -> DocumentationGenerator.generate());

		ToolBar toolBar = new ToolBar(saveButton, loadButton, docButton);

		pane.setCenter(generator.getBoard());
		pane.setTop(toolBar);
		
		return pane;
	}
	
}
