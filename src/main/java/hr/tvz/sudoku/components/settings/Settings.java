package hr.tvz.sudoku.components.settings;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;

import java.util.List;

public class Settings {
	
	private static final List<Integer> SIZES = List.of(4, 9, 16, 25);
	private static final List<String> DIFFICULTIES = List.of("Easy", "Medium", "Hard");

	private int selectedSize;
	private int emptyBoxes;
	private EventHandler<ActionEvent> onConfirm;
	private EventHandler<ActionEvent> onReplay;
	
	
	public Pane construct() {
		GridPane pane = new GridPane();

		Label sizeLabel = new Label("Choose size: ");
		Label difficultyLabel = new Label("Choose difficulty: ");
		ChoiceBox<Integer> sizeChoice = new ChoiceBox<>(FXCollections.observableList(SIZES));
		ChoiceBox<String> difficultyChoice = new ChoiceBox<>(FXCollections.observableList(DIFFICULTIES));
		Button ok = new Button("Ok");
		Button replay = new Button("Watch replay");
		
		pane.add(replay, 0, 0);
		pane.add(sizeLabel, 0, 1);
		pane.add(difficultyLabel, 0, 2);
		pane.add(sizeChoice, 1, 1);
		pane.add(difficultyChoice, 1, 2);
		pane.add(new Text(), 1, 4); // add empty row
		pane.add(ok, 1, 5);

		sizeChoice.getSelectionModel().selectedItemProperty()
				.addListener((observ, old, newVal) -> selectedSize = newVal);
		difficultyChoice.getSelectionModel().selectedItemProperty()
				.addListener((observ, old, newVal) -> emptyBoxes = convertDifficulty(newVal));


		sizeChoice.getSelectionModel().selectFirst();
		difficultyChoice.getSelectionModel().selectFirst();
		
		ok.setOnAction(onConfirm);
		replay.setOnAction(onReplay);
		
		return pane;
	}

	private int convertDifficulty(String difficulty) {
		int index = DIFFICULTIES.indexOf(difficulty);
		if (index == -1) 
			throw new IllegalArgumentException();
		
		double factor = 0.2 + 0.32 * index;
		
		return (int) (selectedSize * selectedSize * factor);
	}

	public void setOnConfirm(EventHandler<ActionEvent> onConfirm) {
		this.onConfirm = onConfirm;
	}
	
	public void setOnReplay(EventHandler<ActionEvent> onReplay) {
		this.onReplay = onReplay;
	}
	
	public int getSize() {
		return selectedSize;
	}

	public int getEmptyBoxes() {
		return emptyBoxes;
	}
}
