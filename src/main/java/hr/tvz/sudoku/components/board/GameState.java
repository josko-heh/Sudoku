package hr.tvz.sudoku.components.board;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;

public class GameState implements Serializable {

	public static class BoardState implements Serializable {
		@Serial
		private static final long serialVersionUID = -2863663855950940270L;
		
		private int[][] current;
		private int[][] solution;
		private boolean[][] isGenerated;

		BoardState(int[][] current, int[][] solution, boolean[][] isGenerated) {
			this.current = current;
			this.solution = solution;
			this.isGenerated = isGenerated;
		}

		public int[][] getCurrent() {
			return current;
		}

		public void setCurrent(int[][] current) {
			this.current = current;
		}

		public int[][] getSolution() {
			return solution;
		}

		public void setSolution(int[][] solution) {
			this.solution = solution;
		}

		public boolean[][] getIsGenerated() {
			return isGenerated;
		}

		public void setIsGenerated(boolean[][] isGenerated) {
			this.isGenerated = isGenerated;
		}
	}
	
	@Serial
	private static final long serialVersionUID = 1L;

	private BoardState boardState;
	private Duration elapsedTime;


	public GameState(BoardState boardState, Duration elapsedTime) {
		this.boardState = boardState;
		this.elapsedTime = elapsedTime;
	}

	public BoardState getBoardState() {
		return boardState;
	}

	public void setBoardState(BoardState boardState) {
		this.boardState = boardState;
	}
	
	public Duration getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Duration elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
}
