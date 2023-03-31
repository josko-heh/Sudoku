package hr.tvz.sudoku.control;

import java.io.Serializable;

public class BoxState implements Serializable {
	
	private String text;
	private String style;

	public BoxState(String text, String style) {
		this.text = text;
		this.style = style;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
}
