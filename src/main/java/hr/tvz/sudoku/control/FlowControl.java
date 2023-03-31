package hr.tvz.sudoku.control;

import hr.tvz.sudoku.components.board.Generator;
import hr.tvz.sudoku.components.settings.Settings;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FlowControl {
	
	public static void initialize(Stage stage) {
		
		Settings settings = new Settings();
		settings.setOnConfirm(event -> {
			Generator generator = new Generator(settings.getSize(), settings.getEmptyBoxes());
			stage.setScene(new Scene(generator.generateBoard()));
		});
		
		Scene initialScene = new Scene(settings.construct());
		stage.setTitle("Sudoku");
		stage.setScene(initialScene);
		stage.show();
	}
}
