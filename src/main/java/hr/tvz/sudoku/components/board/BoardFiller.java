package hr.tvz.sudoku.components.board;

import javafx.scene.control.TextField;

import java.util.Random;

class BoardFiller {

	private final TextField[][] boxes;
	private final int[][] mat;
	private final int size; // number of columns/rows
	private final int sizeSqrt;
	private final int emptyBoxes; // number of missing digits
	private final Random randomGenerator = new Random();

	BoardFiller(TextField[][] boxes, int emptyBoxes) {
		this.boxes = boxes;
		
		this.size = boxes.length;
		this.sizeSqrt = (int) Math.sqrt(size);
		this.mat = new int[size][size];
		
		if (emptyBoxes > size*size)
			this.emptyBoxes = size;
		else
			this.emptyBoxes = emptyBoxes;
	}

	void fill() {
		fillDiagonal();
		fillRemaining(0, sizeSqrt);
		removeDigits();
		fillBoxes();
		// return mat to beginning state if reused
	}

	// Fill the diagonal sizeSqrt number of sizeSqrt x sizeSqrt matrices
	private void fillDiagonal() {
		for (int i = 0; i < size; i = i + sizeSqrt)
			// for diagonal box, start coordinates->i==j
			fillRegion(i);
	}

	// Fill a sizeSqrt x sizeSqrt matrix.
	private void fillRegion(int indexOfFirst) {
		int num;
		for (int i = 0; i < sizeSqrt; i++) {
			for (int j = 0; j < sizeSqrt; j++) {
				do {
					num = randomGenerator.nextInt(size+1);
				} while (!unusedInRegion(indexOfFirst, indexOfFirst, num));

				mat[indexOfFirst + i][indexOfFirst + j] = num;
			}
		}
	}

	// Returns false if given region contains num.
	private boolean unusedInRegion(int rowStart, int colStart, int num) {
		for (int i = 0; i < sizeSqrt; i++)
			for (int j = 0; j < sizeSqrt; j++)
				if (mat[rowStart + i][colStart + j] == num) return false;

		return true;
	}

	// Check if safe to put in cell
	private boolean checkIfSafe(int i, int j, int num) {
		return (unusedInRow(i, num) && unusedInCol(j, num) 
				&& unusedInRegion(i - i % sizeSqrt, j - j % sizeSqrt, num));
	}

	// Check in the row for existence
	private boolean unusedInRow(int i, int num) {
		for (int j = 0; j < size; j++)
			if (mat[i][j] == num) return false;
		return true;
	}

	// Check in the row for existence
	private boolean unusedInCol(int j, int num) {
		for (int i = 0; i < size; i++)
			if (mat[i][j] == num) return false;
		return true;
	}

	// A recursive function to fill remaining matrix
	private boolean fillRemaining(int i, int j) {
		if (j >= size && i < size - 1) {
			i = i + 1;
			j = 0;
		}
		if (i >= size && j >= size) return true;

		if (i < sizeSqrt) {
			if (j < sizeSqrt) j = sizeSqrt;
		} else if (i < size - sizeSqrt) {
			if (j == (i / sizeSqrt) * sizeSqrt) j = j + sizeSqrt;
		} else {
			if (j == size - sizeSqrt) {
				i = i + 1;
				j = 0;
				if (i >= size) return true;
			}
		}

		for (int num = 1; num <= size; num++) {
			if (checkIfSafe(i, j, num)) {
				mat[i][j] = num;
				if (fillRemaining(i, j + 1))
					return true;
				mat[i][j] = 0;
			}
		}
		
		return false;
	}

	// Remove 'emptyBoxes' of digits to complete the game
	private void removeDigits() {
		int count = emptyBoxes;
		while (count != 0) {
			int cellId = randomGenerator.nextInt(size*size);

			int i = (cellId / size);
			int j = cellId % size;
			
			if (mat[i][j] != 0) {
				count--;
				mat[i][j] = 0;
			}
		}
	}

	// Copy numbers from mat to boxes
	private void fillBoxes() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (mat[i][j] == 0)
					boxes[i][j].setText("");
				else 
					boxes[i][j].setText(String.valueOf(mat[i][j]));
			}
		}
	}
}