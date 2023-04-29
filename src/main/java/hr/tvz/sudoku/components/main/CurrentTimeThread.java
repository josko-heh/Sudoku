package hr.tvz.sudoku.components.main;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static hr.tvz.sudoku.util.Styling.TIME_FORMAT;

public class CurrentTimeThread extends Thread {
	private final Label label;

	public CurrentTimeThread(Label label) {
		this.label = label;
	}

	@Override
	public void run() {
		while (true) {
			Platform.runLater(() -> label.setText(
					LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_FORMAT))));

			try {
				Thread.sleep(1000);
			} catch (InterruptedException ignored) { }
		}
	}
}
