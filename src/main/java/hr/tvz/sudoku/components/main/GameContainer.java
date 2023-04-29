package hr.tvz.sudoku.components.main;

import hr.tvz.sudoku.components.board.GameState;
import hr.tvz.sudoku.components.board.Generator;
import hr.tvz.sudoku.control.SaveHandler;
import hr.tvz.sudoku.components.main.documentation.DocumentationGenerator;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.Instant;

import static hr.tvz.sudoku.components.main.replay.ReplayXmlUtil.createReplayFiles;

public class GameContainer {
	
	private Generator generator;

	public GameContainer(int size, int emptyBoxes) {
		this.generator = new Generator(size, emptyBoxes);
	}

	public Pane generate() {
		BorderPane pane = new BorderPane();

		/* Time */
		Timer timer = new Timer();
		HBox elapsedTime = new HBox(new Label("Elapsed time: "), timer.getElapsedTimeLabel());

		Label currTimeLabel = new Label();
		Thread currTimeThread = new CurrentTimeThread(currTimeLabel);
		HBox currTime = new HBox(new Label("Current time: "), currTimeLabel);
		currTimeThread.start();

		VBox time = new VBox(currTime, elapsedTime);
		
		/* Buttons */
		Button saveButton = new Button("Save");
		saveButton.setOnAction(event -> SaveHandler.save(new GameState(generator.getState(), timer.getElapsedTime())));
		
		Button loadButton = new Button("Load");
		loadButton.setOnAction(event -> SaveHandler.load().ifPresent(gameState -> {
			timer.setStart(Instant.now().minus(gameState.getElapsedTime()));
			
			generator = new Generator(gameState.getBoardState());
			pane.setCenter(generator.getBoard());
		}));
		
		Button docButton = new Button("Documentation");
		docButton.setOnAction(event -> DocumentationGenerator.generate());

		Button replayButton = new Button("Save replay");
		replayButton.setOnAction(event -> createReplayFiles(generator.getMoves(), generator.getInitialState()));
		
		ToolBar toolBar = new ToolBar(saveButton, loadButton, docButton, replayButton);


		pane.setCenter(generator.getBoard());
		pane.setTop(toolBar);
		pane.setBottom(time);
		
		return pane;
	}
	
}
