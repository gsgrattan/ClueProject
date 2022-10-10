package experiment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestBoard {

	private Set<TestBoardCell> targets;
	private List<List<TestBoardCell>> board;

	// Constructor, fills board with default TestBoardCell Objects
	public TestBoard(int rows, int columns) {

		targets = new HashSet<TestBoardCell>();
		board = new ArrayList<List<TestBoardCell>>(rows);

		for (int i = 0; i < rows; i++) {
			ArrayList<TestBoardCell> r = new ArrayList<TestBoardCell>(columns);
			for (int j = 0; j < columns; j++) {
				r.add(new TestBoardCell(i, j));
			}
			this.board.add(r);
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				if (i - 1 >= 0) {
					TestBoardCell adj = this.board.get(i - 1).get(j);
					this.board.get(i).get(j).addAdjacency(adj);
				}

				if (i + 1 < rows) {
					TestBoardCell adj = this.board.get(i + 1).get(j);
					this.board.get(i).get(j).addAdjacency(adj);
				}

				if (j - 1 >= 0) {
					TestBoardCell adj = this.board.get(i).get(j - 1);
					this.board.get(i).get(j).addAdjacency(adj);
				}

				if (j + 1 < rows) {
					TestBoardCell adj = this.board.get(i).get(j + 1);
					this.board.get(i).get(j).addAdjacency(adj);
				}
			}
		}
	}

	// Return a specific cell from the targets I assume.
	public TestBoardCell getCell(int row, int column) {
		return board.get(row).get(column);
	}

	// Calculates all the targets and stores them in the targets set
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		this.calcTargets(startCell, pathlength, new HashSet<>());
	}

	private void calcTargets(TestBoardCell startCell, int pathlength, Set<TestBoardCell> visited) {
		if (visited.isEmpty()) {
			visited.add(startCell);
		}

		if (pathlength == 0 || startCell.getRoom()) {
			targets.add(startCell);
		} else {
			for (TestBoardCell cell : startCell.getAdjList()) {
				if (!cell.getOccupied() && !visited.contains(cell)) {
					visited.add(cell);
					calcTargets(cell, pathlength - 1, visited);
					visited.remove(cell);
				}
			}
		}
	}

	// return the targets list
	public Set<TestBoardCell> getTargets() {
		return targets;

	}

}
