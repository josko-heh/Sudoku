package hr.tvz.sudoku.startup;

import hr.tvz.sudoku.generator.Generator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SudokuApp extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) {
		Generator generator = new Generator(9, 20);
		
		Scene scene = new Scene(generator.generateBoard());
		stage.setTitle("Sudoku");
		stage.setScene(scene);
		stage.show();
	}
	
}