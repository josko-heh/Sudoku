package hr.tvz.sudoku.components.replay;

import hr.tvz.sudoku.components.board.GameMove;
import hr.tvz.sudoku.components.board.GameState;
import hr.tvz.sudoku.components.board.Generator;
import hr.tvz.sudoku.control.SaveHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Optional;

import static hr.tvz.sudoku.components.replay.ReplayXmlUtil.INITIAL_STATE_FILE_NAME;

public class Replay {

	public static Optional<Pane> generate() {
		BorderPane pane = new BorderPane();

		Optional<GameState.BoardState> initialState = SaveHandler.load(INITIAL_STATE_FILE_NAME);
		Optional<List<GameMove>> moves = ReplayXmlUtil.load();
		
		if (initialState.isEmpty() || moves.isEmpty()) 
			return Optional.empty();

		Generator generator = new Generator(initialState.get());
		pane.setCenter(generator.getBoard());

		generator.playMoves(moves.get());
		
		return Optional.of(pane);
	}
}
