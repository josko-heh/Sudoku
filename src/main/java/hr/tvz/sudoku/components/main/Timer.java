package hr.tvz.sudoku.components.main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;

import java.time.Duration;
import java.time.Instant;

public class Timer {

	private Instant start = Instant.now();
	
	public Label getElapsedTimeLabel() {
		Label timeLabel = new Label();
		
		Timeline clock = new Timeline(
				new KeyFrame(
						javafx.util.Duration.ZERO,
						e -> timeLabel.setText(getElapsedTime().toSeconds() + " s")),
				new KeyFrame(javafx.util.Duration.seconds(1))
		);
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();

		return timeLabel;
	}

	public void setStart(Instant start) {
		this.start = start;
	}

	public Duration getElapsedTime() {
		return Duration.between(start, Instant.now());
	}
}
