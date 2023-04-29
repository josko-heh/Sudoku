package hr.tvz.sudoku.components.main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import java.time.Duration;

import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static hr.tvz.sudoku.util.Styling.TIME_FORMAT;

public class Timer {

	private Timer() { }
	
	public static Label getElapsedTime() {
		Label timeLabel = new Label();
		Instant start = Instant.now();

		Timeline clock = new Timeline(
				new KeyFrame(
						javafx.util.Duration.ZERO,
						e -> timeLabel.setText(Duration.between(start, Instant.now()).toSeconds() + " s")),
				new KeyFrame(javafx.util.Duration.seconds(1))
		);
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();

		return timeLabel;
	}

	static class CurrentTimeThread extends Thread {
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
}
