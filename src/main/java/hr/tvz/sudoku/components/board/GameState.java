package hr.tvz.sudoku.components.board;

import java.io.Serial;
import java.io.Serializable;

public class GameState implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	
	private int[][] current;
	private int[][] solution;
	private boolean[][] isGenerated;

	GameState(int[][] current, int[][] solution, boolean[][] isGenerated) {
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
