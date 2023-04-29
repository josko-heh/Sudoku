package hr.tvz.sudoku.components.board;

public class GameMove {
	
	private int number;
	private int row;
	private int col;

	public GameMove(int number, int row, int col) {
		this.number = number;
		this.row = row;
		this.col = col;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
}
