package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {

	private char cellLabel;
	private int row;
	private int col;
	private DoorDirection doorDirection;
	private Boolean roomLabel = false;
	private Boolean roomCenter = false;
	private Boolean isDoorway = false;
	private char secretPassage = '\0';

	private Set<BoardCell> adjacencyList;

	/*
	 * initializer for Boardcell object
	 */
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		this.adjacencyList = new HashSet<BoardCell>();

	}

//Get the label
	public char getCellLabel() {
		return cellLabel;
	}

	// set the label
	public void setCellLabel(char cellLabel) {
		this.cellLabel = cellLabel;
	}

	// return True if the cell is the room label
	public Boolean getRoomLabel() {
		return roomLabel;
	}

	// Set the boolean for the room label
	public void setRoomLabel(Boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	// return true if the cell is the center
	public Boolean getRoomCenter() {
		return roomCenter;
	}

	// Set Boolean for center of the room
	public void setRoomCenter(Boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	// Returns the adacency list
	public Set<BoardCell> getAdjList() {
		return adjacencyList;
	}

	// set the direction of the door
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
		this.isDoorway = true;
	}

	// Set the secret passage destination
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	// Set the boolean if it is a doorwar
	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return this.isDoorway;
	}

	// Returns the door direciton
	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return this.doorDirection;
	}

	// Set the boolean to True if the room is the label
	public boolean isLabel() {
		// TODO Auto-generated method stub
		return roomLabel;
	}

	// Return true if the cell is the center of the room
	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return roomCenter;
	}

	// return the destination of the secret passage
	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return secretPassage;
	}

	// Add a value to the adjacency list
	public void addAdjacency(BoardCell adj) {
		this.adjacencyList.add(adj);
	}

	public void setOccupied(boolean b) {
		// TODO Auto-generated method stub

	}

}
