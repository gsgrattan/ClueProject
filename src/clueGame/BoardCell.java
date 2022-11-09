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
	private Boolean isOccupied = false;
	private Card card = null;

	private Set<BoardCell> adjacencyList;

	/*
	 * initializer for Boardcell object
	 */
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		this.adjacencyList = new HashSet<BoardCell>();

	}

	// Get the label
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
		return this.isDoorway;
	}

	// Get the row of the BoardCell
	public int getRow() {
		return row;
	}

	// Get the col of the BoardCell
	public int getCol() {
		return col;
	}

	// Returns the door direciton
	public DoorDirection getDoorDirection() {
		return this.doorDirection;
	}

	// Set the boolean to True if the room is the label
	public boolean isLabel() {
		return roomLabel;
	}

	// Return true if the cell is the center of the room
	public boolean isRoomCenter() {
		return roomCenter;
	}

	// return the destination of the secret passage
	public char getSecretPassage() {
		return secretPassage;
	}

	// Add a value to the adjacency list
	public void addAdjacency(BoardCell adj) {
		this.adjacencyList.add(adj);
	}

	// Set the boolean if the cell is occupied
	public void setOccupied(boolean b) {
		this.isOccupied = b;
	}

	// Get if the cell is occupied
	public boolean getOccupied() {
		return this.isOccupied;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Card getCard() {
		return this.card;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + "]";
	}
}
