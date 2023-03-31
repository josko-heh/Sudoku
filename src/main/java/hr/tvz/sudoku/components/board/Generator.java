package hr.tvz.sudoku.components.board;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Arrays;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Generator {

	private final int size;
	private final GridPane board;
	private final TextField[][] boxes;
	private final BoardStyler boardStyler;
	private final BoardFiller filler;
	
	public Generator(int size, int emptyBoxes) {
		this.size = size;

		board = new GridPane();
		boxes = new TextField[size][size];
		boardStyler = new BoardStyler(boxes, board.widthProperty(), board.heightProperty());
		filler = new BoardFiller(boxes, emptyBoxes);
	}
	
	
	public GridPane generateBoard() {
		board.setVgap(0);
		board.setHgap(0);
		board.setAlignment(Pos.CENTER);

		generateBoxes();
		filler.fill();
		boardStyler.set();
		disableInitialDigits();

		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes.length; j++) {
				board.add(boxes[i][j], j, i);
			}
		}
		
		return board;
	}
	
	private void generateBoxes() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {

				TextField box = new TextField();

				box.textProperty().addListener(new ChangeListener<>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, 
										String oldValue, String newValue) {
						if (!isValidInputValue(newValue)) {
							box.textProperty().removeListener(this);
							box.setText(oldValue);
							box.textProperty().addListener(this);
						}
					}
				});
				
				boxes[row][col] = box;
			}
		}
	}
	
	private boolean isValidInputValue(String val) {
		int intVal;
		try {
			intVal = Integer.parseInt(val);
		} catch (NumberFormatException e) {
			return false;
		}

		return intVal <= size && intVal > 0;
	}
	
	private void disableInitialDigits() {
		Arrays.stream(boxes)
				.flatMap(Arrays::stream)
				.filter(field -> !isBlank(field.getText()))
				.forEach(blankBox -> {
					blankBox.setEditable(false);
					blankBox.setMouseTransparent(true);
					blankBox.setFocusTraversable(false);
				});
	}
}
