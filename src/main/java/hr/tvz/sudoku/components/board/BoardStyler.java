package hr.tvz.sudoku.components.board;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Arrays;

import static hr.tvz.sudoku.util.Styling.addStyle;
import static org.apache.commons.lang3.StringUtils.isBlank;

class BoardStyler {

	private static final BorderWidths bottomRight = new BorderWidths(1, 3, 3, 1);
	private static final BorderWidths bottom = new BorderWidths(1, 1, 3, 1);
	private static final BorderWidths right = new BorderWidths(1, 3, 1, 1);
	private static final BorderWidths noBorder = new BorderWidths(1, 1, 1, 1);

	private final TextField[][] boxes;
	private final int size;
	private final int sizeSqrt;
	private final ReadOnlyDoubleProperty parentWidth;
	private final ReadOnlyDoubleProperty parentHeight;

	BoardStyler(TextField[][] boxes, ReadOnlyDoubleProperty parentWidth, ReadOnlyDoubleProperty parentHeight) {
		this.boxes = boxes;
		this.parentWidth = parentWidth;
		this.parentHeight = parentHeight;
		
		size = boxes.length;
		sizeSqrt = (int) Math.sqrt(size);
	}

	void set(){
		setBoxesStyling();
		setDigitsColorInFilledBoard();
		setSize();
	}


	private void setBoxesStyling() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				TextField box = boxes[row][col];
				
				box.setBorder(new Border(new BorderStroke(
						Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, determineBoxBorderWidths(row, col))));
				box.setMinSize(38, 38);
				box.setMaxSize(55, 55);
				box.setAlignment(Pos.CENTER);
				addStyle(box, "-fx-font-size: 14px;");
			}
		}
	}

	private void setDigitsColorInFilledBoard() {
		Arrays.stream(boxes)
				.flatMap(Arrays::stream)
				.filter(field -> isBlank(field.getText()))
				.forEach(blankBox -> addStyle(blankBox, "-fx-text-fill: #0066ff;"));
	}
	
	// Sets size to keep 1:1 aspect ratio while resizing.
	private void setSize() {
		NumberBinding boxSize = Bindings.min(parentWidth.divide(this.size), parentHeight.divide(this.size));

		Arrays.stream(boxes)
				.flatMap(Arrays::stream)
				.forEach(box -> {
					box.prefWidthProperty().bind(boxSize);
					box.prefHeightProperty().bind(boxSize);
				});
	}
	
	private BorderWidths determineBoxBorderWidths(int row, int col) {
		BorderWidths boxWidths = noBorder;

		if (row % sizeSqrt == sizeSqrt-1 && col % sizeSqrt == sizeSqrt-1 && col != size -1 && row != size -1)
			boxWidths = bottomRight;
		else if (col % sizeSqrt == sizeSqrt-1 && col != size -1)
			boxWidths = right;
		else if (row % sizeSqrt == sizeSqrt-1 && row != size -1)
			boxWidths = bottom;

		return boxWidths;
	}
}
