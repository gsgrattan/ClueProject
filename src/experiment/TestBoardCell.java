package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private final static int NUM_ADJACENT = 8;
	private int row;
	private int column;

	private boolean isOccupied = false;
	private boolean isRoom = false;

	private Set<TestBoardCell> adjacencyList;

	// Constructor
	public TestBoardCell(int row, int column) {
		this.row = row;
		this.column = column;
		this.adjacencyList = new HashSet<TestBoardCell>();
	}

	// Set if the cell is occupied
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	// Get if the cell is occupied
	public boolean getOccupied() {
		return this.isOccupied;
	}

	// Set if the cell is a room
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}

	// Get if the cell is a room
	public boolean getRoom() {
		return this.isRoom;

	}

	// Return the adjacency list (all cells adjancent to this one)
	public Set<TestBoardCell> getAdjList() {
		return adjacencyList;
	}

	// Add an cell to the adjacency list
	public void addAdjacency(TestBoardCell cell) {

	}

}
