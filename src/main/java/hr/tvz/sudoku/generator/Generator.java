package hr.tvz.sudoku.generator;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Generator {

	private static final int SIZE = 16;
	private static final int SIZE_SQRT = (int) Math.sqrt(SIZE);
	private static final int EMPTY_BOXES = 20;
	private static final BorderWidths bottomRight = new BorderWidths(1, 3, 3, 1);
	private static final BorderWidths bottom = new BorderWidths(1, 1, 3, 1);
	private static final BorderWidths right = new BorderWidths(1, 3, 1, 1);
	private static final BorderWidths noBorder = new BorderWidths(1, 1, 1, 1);
	
	
	private Generator() {}
	
	public static GridPane generateBoard() {
		GridPane board = new GridPane();
		board.setVgap(0);
		board.setHgap(0);
		board.setAlignment(Pos.CENTER);

		TextField[][] boxes = generateBoxes();

		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes.length; j++) {
				board.add(boxes[i][j], j, i);

				// keep 1:1 aspect ratio while resizing
				boxes[i][j].prefWidthProperty().bind(
						Bindings.min(board.widthProperty().divide(SIZE),
						board.heightProperty().divide(SIZE)));
				boxes[i][j].prefHeightProperty().bind(
						Bindings.min(board.widthProperty().divide(SIZE),
						board.heightProperty().divide(SIZE)));
			}
		}
		
		return board;
	}
	
	private static TextField[][] generateBoxes() {
		TextField[][] boxes = new TextField[SIZE][SIZE];
		
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {

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

				box.setBorder(new Border(
					new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, getBorderWidths(row, col))));
				box.setMinSize(35, 35);
				box.setMaxSize(55, 55);
				box.setAlignment(Pos.CENTER);
				
				boxes[row][col] = box;
			}
		}

		GridFiller filler = new GridFiller(boxes, EMPTY_BOXES);
		filler.fill();
		
		return boxes;
	}

	private static BorderWidths getBorderWidths(int row, int col) {
		BorderWidths boxWidths = noBorder;

		if (row % SIZE_SQRT == SIZE_SQRT-1 && col % SIZE_SQRT == SIZE_SQRT-1 && col != SIZE -1 && row != SIZE -1)
			boxWidths = bottomRight;
		else if (col % SIZE_SQRT == SIZE_SQRT-1 && col != SIZE -1)
			boxWidths = right;
		else if (row % SIZE_SQRT == SIZE_SQRT-1 && row != SIZE -1)
			boxWidths = bottom;
		
		return boxWidths;
	}
	
	private static boolean isValidInputValue(String val) {
		int intVal;
		try {
			intVal = Integer.parseInt(val);
		} catch (NumberFormatException e) {
			return false;
		}

		return intVal <= SIZE && intVal > 0;
	}
}
