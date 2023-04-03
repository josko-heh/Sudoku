package hr.tvz.sudoku.components.board;

import javafx.scene.control.TextField;

class IntField extends TextField {

	IntField() {
		super();
	}
	
	IntField(int value) {
		super();
		setValue(value);
	}

	int getValue() {
		try {
			return Integer.parseInt(getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	void setValue(int value) {
		if (value <= 0)
			setText("");
		else
			setText(String.valueOf(value));
	}
}
