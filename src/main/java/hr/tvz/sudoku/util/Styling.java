package hr.tvz.sudoku.util;

import javafx.scene.Node;

public class Styling {

	public static final String TIME_FORMAT = "H:m:s";

	private Styling() {}
	
	public static void addStyle(Node node, String style) {
		node.setStyle(node.getStyle() + " " + style);
	}
}
