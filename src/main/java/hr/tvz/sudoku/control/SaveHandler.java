package hr.tvz.sudoku.control;

import hr.tvz.sudoku.components.board.GameState;

import java.io.*;
import java.util.Optional;

import static hr.tvz.sudoku.util.InformUser.showMessage;

public class SaveHandler {
	
	private static final String SAVE_GAME_FILE_NAME = "saveGame.bin";
	
	private SaveHandler() {}
	
	public static void save(GameState state) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_GAME_FILE_NAME)))
		{
			oos.writeObject(state);
			showMessage("Save", "Successfully saved the game!", "");
		} catch (IOException e) {
			e.printStackTrace();
			showMessage("Save", "Failed to save the game!", e.getMessage());
		}
	}

	public static Optional<GameState> load() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_GAME_FILE_NAME)))
		{
			GameState loadedState = (GameState) ois.readObject();
			return Optional.of(loadedState);
		} catch (IOException | ClassNotFoundException | ClassCastException e) {
			e.printStackTrace();
			showMessage("Load", "Failed to load the game!", e.getMessage());
			return Optional.empty();
		}
	}

	public static Optional<GameState.BoardState> load(String fileName) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
			GameState.BoardState loadedState = (GameState.BoardState) ois.readObject();
			return Optional.of(loadedState);
		} catch (IOException | ClassNotFoundException | ClassCastException e) {
			e.printStackTrace();
			showMessage("Load", "Failed to load " + fileName + " file!", e.getMessage());
			return Optional.empty();
		}
	}


	public static void save(GameState.BoardState state, String fileName) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName)))
		{
			oos.writeObject(state);
		}
	}
}
