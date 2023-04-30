package hr.tvz.sudoku.control;

import hr.tvz.sudoku.components.main.GameContainer;
import hr.tvz.sudoku.components.replay.Replay;
import hr.tvz.sudoku.components.settings.Settings;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FlowControl {
	
	public static void initialize(Stage stage) {
		Settings settings = new Settings();
		settings.setOnConfirm(event -> {
			GameContainer gameContainer = new GameContainer(settings.getSize(), settings.getEmptyBoxes());
			stage.setScene(new Scene(gameContainer.generate()));
		});
		settings.setOnReplay(event -> Replay.generate().ifPresent(replay -> {
				stage.setScene(new Scene(replay));
				replay.run();
		}));
		
		Scene initialScene = new Scene(settings.construct());
		
		stage.setTitle("Sudoku");
		stage.setScene(initialScene);
		stage.show();
	}
}
