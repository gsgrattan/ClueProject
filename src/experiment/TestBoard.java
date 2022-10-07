package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {

	private Set<TestBoardCell> targets;

	// Constructor
	public TestBoard() {
		targets = new HashSet<TestBoardCell>();
	}

	// Return a specific cell from the targets I assume.
	public TestBoardCell getCell(int row, int column) {
		return new TestBoardCell(row, column);
	}

	// Calculates all the targets and stores them in the targets set
	public void calcTargets(TestBoardCell startCell, int pathlength) {
	}

	// return the targets list
	public Set<TestBoardCell> getTargets() {
		return targets;

	}

}
