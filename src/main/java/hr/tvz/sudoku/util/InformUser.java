package hr.tvz.sudoku.util;

import javafx.scene.control.Alert;

public class InformUser {

	private InformUser() {}
	
	public static void showMessage(String title, String header, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
