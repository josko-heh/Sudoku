package hr.tvz.sudoku.components.main;

import hr.tvz.sudoku.components.board.Generator;
import hr.tvz.sudoku.control.SaveHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class GameContainer {
	
	private final Generator generator;

	public GameContainer(int size, int emptyBoxes) {
		this.generator = new Generator(size, emptyBoxes);
	}

	public Pane generate() {
		Button saveButton = new Button("Save");
		saveButton.setOnAction(event -> SaveHandler.save(generator));
		
		ToolBar toolBar = new ToolBar(saveButton);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(generator.generateBoard());
		pane.setTop(toolBar);

		return pane;
	}
}
