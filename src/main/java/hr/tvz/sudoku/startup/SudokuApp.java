package hr.tvz.sudoku.startup;

import hr.tvz.sudoku.control.FlowControl;
import javafx.application.Application;
import javafx.stage.Stage;


public class SudokuApp extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) {
		FlowControl.initialize(stage);
	}
	
}