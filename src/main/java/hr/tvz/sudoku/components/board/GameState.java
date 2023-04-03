package hr.tvz.sudoku.components.board;

import java.io.Serial;
import java.io.Serializable;

public class GameState implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	
	private int[][] current;
	private int[][] solution;

	GameState(int[][] current, int[][] solution) {
		this.current = current;
		this.solution = solution;
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
}
