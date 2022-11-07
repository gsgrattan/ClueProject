package clueGame;

public class ComputerPlayer extends Player {
	// constructor
	public ComputerPlayer(String name) {
		super(name);
	}

	public Solution createSuggestion() {
		return new Solution(null, null, null);

	}

	public BoardCell selectTarget() {
		return new BoardCell(0, 0);

	}

}
