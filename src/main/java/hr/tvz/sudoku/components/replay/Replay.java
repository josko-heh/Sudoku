package hr.tvz.sudoku.components.replay;

import hr.tvz.sudoku.components.board.GameMove;
import hr.tvz.sudoku.components.board.GameState;
import hr.tvz.sudoku.components.board.Generator;
import hr.tvz.sudoku.control.SaveHandler;
import javafx.scene.layout.BorderPane;

import java.util.List;
import java.util.Optional;

import static hr.tvz.sudoku.components.replay.ReplayXmlUtil.INITIAL_STATE_FILE_NAME;

public class Replay extends BorderPane implements Runnable {

	private final Generator generator;
	private final List<GameMove> moves;

	private Replay(Generator generator, List<GameMove> moves) {
		this.generator = generator;
		this.moves = moves;

		setCenter(generator.getBoard());
	}

	public static Optional<Replay> generate() {
		Optional<GameState.BoardState> initialState = SaveHandler.load(INITIAL_STATE_FILE_NAME);
		Optional<List<GameMove>> moves = ReplayXmlUtil.load();
		
		if (initialState.isEmpty() || moves.isEmpty()) 
			return Optional.empty();

		Replay replayPane = new Replay(new Generator(initialState.get()), moves.get());
		
		return Optional.of(replayPane);
	}

	@Override
	public void run() {
		generator.playMoves(moves);
	}

}
