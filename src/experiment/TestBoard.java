package experiment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestBoard {

	private Set<TestBoardCell> targets;
	private List<List<TestBoardCell>> board;

	// Constructor
	public TestBoard(int rows, int columns) {
		targets = new HashSet<TestBoardCell>();
		board = new ArrayList<List<TestBoardCell>>(rows);

		for (int i = 0; i < rows; i++) {
			ArrayList<TestBoardCell> r = new ArrayList<TestBoardCell>(columns);
			for (int j = 0; j < columns; j++) {
				r.add(new TestBoardCell());
			}
			this.board.add(r);
		}
	}

	// Return a specific cell from the targets I assume.
	public TestBoardCell getCell(int row, int column) {
		return board.get(row).get(column);
	}

	// Calculates all the targets and stores them in the targets set
	public void calcTargets(TestBoardCell startCell, int pathlength) {
	}

	// return the targets list
	public Set<TestBoardCell> getTargets() {
		return targets;

	}

}
