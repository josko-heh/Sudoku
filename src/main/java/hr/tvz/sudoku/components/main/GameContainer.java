package hr.tvz.sudoku.components.main;

import hr.tvz.sudoku.components.board.Generator;
import hr.tvz.sudoku.control.SaveHandler;
import hr.tvz.sudoku.documentation.DocumentationGenerator;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static hr.tvz.sudoku.components.main.Timer.getElapsedTime;

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

		HBox elapsedTime = new HBox(new Label("Elapsed time: "), getElapsedTime());
		Label currTimeLabel = new Label();
		Thread currTimeThread = new Timer.CurrentTimeThread(currTimeLabel);
		HBox currTime = new HBox(new Label("Current time: "), currTimeLabel);
		currTimeThread.start();
		
		VBox time = new VBox(currTime, elapsedTime);

		pane.setCenter(generator.getBoard());
		pane.setTop(toolBar);
		pane.setBottom(time);
		
		return pane;
	}
	
}
