package hr.tvz.sudoku.control;

import hr.tvz.sudoku.components.board.Generator;
import javafx.scene.control.TextField;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static hr.tvz.sudoku.util.InformUser.showMessage;

public class SaveHandler {
	
	private static final String SAVE_GAME_FILE_NAME = "saveGame.bin";
	
	private SaveHandler() {}
	
	public static void save(Generator generator) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_GAME_FILE_NAME)))
		{
			oos.writeObject(convert(generator.getBoxes()));
			showMessage("Save", "Successfully saved the game!", "");
		} catch (IOException e) {
			e.printStackTrace();
			showMessage("Save", "Failed to save the game!", e.getMessage());
		}
	}
	
	private static BoxState[][] convert(TextField[][] boxes) {
		int size = boxes.length;
		BoxState[][] states = new BoxState[size][size];


		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				TextField box = boxes[i][j];
				states[i][j] = new BoxState(box.getText(), box.getStyle());
			}
		}
		
		return states;
	}
}
