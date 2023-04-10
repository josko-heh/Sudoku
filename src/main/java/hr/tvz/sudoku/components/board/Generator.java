package hr.tvz.sudoku.components.board;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Arrays;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Generator {

	private final int size;
	private final GridPane board;
	private final IntField[][] boxes;
	private final int[][] solution;
	private final BoardStyler boardStyler;
	private final BoardFiller filler;
	private final Label correctCountLabel;

	public Generator(int size, int emptyBoxes) {
		this.size = size;

		board = new GridPane();
		prepareBoard();
		boxes = new IntField[size][size];
		boardStyler = new BoardStyler(boxes, board.widthProperty(), board.heightProperty());
		filler = new BoardFiller(boxes, emptyBoxes);
		correctCountLabel = new Label();

		board.add(correctCountLabel, boxes.length, boxes.length);
		initializeBoxes();
		filler.fill();
		boardStyler.style();
		disableInitialDigits();
		fillBoardWithBoxes();
		solution = filler.getSolution();
		addBoxesInputListener();
	}

	public Generator(GameState state) {
		solution = state.getSolution();
		
		size = solution.length;
		board = new GridPane();
		prepareBoard();
		boxes = new IntField[size][size];
		boardStyler = new BoardStyler(boxes, board.widthProperty(), board.heightProperty());
		filler = null;
		correctCountLabel = new Label();

		board.add(correctCountLabel, boxes.length, boxes.length);
		initializeBoxes(state.getCurrent());
		boardStyler.style();
		disableInitialDigits();
		addBoxesInputListener();
		fillBoardWithBoxes();
	}
	
	
	private void prepareBoard() {
		board.setVgap(0);
		board.setHgap(0);
		board.setAlignment(Pos.CENTER);
	}

	private void initializeBoxes() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				boxes[row][col] = new IntField();
			}
		}
	}

	private void initializeBoxes(int[][] digits) {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				boxes[row][col] = new IntField(digits[row][col]);
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
	
	private void addBoxesInputListener() {
		Arrays.stream(boxes)
			.flatMap(Arrays::stream)
			.filter(field -> field.getValue() == 0)
			.forEach(box -> box.textProperty().addListener(new ChangeListener<>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!isValidInputValue(newValue)) {
						box.textProperty().removeListener(this);
						box.setText(oldValue);
						box.textProperty().addListener(this);
					} else
						correctCountLabel.setText(String.valueOf(getCorrectBoxes()));
				}
			}));
	}

	private void fillBoardWithBoxes() {
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes.length; j++) {
				board.add(boxes[i][j], j, i);
			}
		}
	}

	private int getCorrectBoxes() {
		int count = 0;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				try {
					int currValue = boxes[i][j].getValue();
					if (currValue != 0 && solution[i][j] == currValue)
						count++;
				} catch (NumberFormatException ignored) { }
			}
		}

		return count;
	}

	private int[][] getCurrentDigits() {
		int[][] current = new int[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				current[i][j] = boxes[i][j].getValue();
			}
		}
		
		return current;
	}
	
	public GameState getState() {
		return new GameState(getCurrentDigits(), solution);
	}

	public GridPane getBoard() {
		return board;
	}
}
