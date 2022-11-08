package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {
	// constructor
	public ComputerPlayer(String name) {
		super(name);
	}

	public Solution createSuggestion() {
		return new Solution(null, null, null);

	}

	public BoardCell selectTarget(Set<BoardCell> targets) {

		return new BoardCell(0, 0);

	}

}
