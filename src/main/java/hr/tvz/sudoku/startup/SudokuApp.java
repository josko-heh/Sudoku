package hr.tvz.sudoku.startup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class SudokuApp extends Application {
	
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(SudokuApp.class.getResource("/graphical/mainScreen.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 600, 600);
		stage.setTitle("Sudoku");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}