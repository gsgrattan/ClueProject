package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private final static int NUM_ADJACENT = 4;

	private boolean isOccupied = false;
	private boolean isRoom = false;
	private boolean isDoor = false;

	private Set<TestBoardCell> adjacencyList;

	// Constructor
	public TestBoardCell() {
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

	// Set if the cell is a door
	public void setIsDoor(boolean isDoor) {
		this.isDoor = isDoor;
	}

	// Get if the cell is a door
	public boolean getDoor() {
		return this.isDoor;
	}

	// Return the adjacency list (all cells adjancent to this one)
	public Set<TestBoardCell> getAdjList() {
		return adjacencyList;
	}

	// Add an cell to the adjacency list
	public void addAdjacency(TestBoardCell cell) {
		this.adjacencyList.add(cell);
	}

}
