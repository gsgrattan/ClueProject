package clueGame;

import java.awt.Color;
import java.awt.Graphics;
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

	public void draw(Graphics g, int cellWidth, int cellHeight) {
		// Calculate the Cell's current Location
		int x = cellWidth * col;
		int y = cellHeight * row;

		// Go through a few different cases of cel type

		// if it's an empty space
		if (cellLabel == 'X') {
			g.setColor(Color.black);
			g.fillRect(x, y, cellWidth, cellHeight);

			// if its a walkway
		} else if (cellLabel == 'W') {
			// Draw the yellow background
			g.setColor(Color.yellow);
			g.fillRect(x, y, cellWidth, cellHeight);
			// Draw the black border
			g.setColor(Color.black);
			g.drawRect(x, y, cellWidth, cellHeight);

			// Otherwise its a room
		} else {
			g.setColor(Color.gray);
			g.fillRect(x, y, cellWidth, cellHeight);

		}
	}

	public void drawDoorway(Graphics g, int cellWidth, int cellHeight) {

		// Redundancy check, make sure the dor is a doorway
		if (this.isDoorway()) {
			// Create values for the door length width and position
			int doorWidth;
			int doorLength;
			int x;
			int y;

			// set the doorcolor
			g.setColor(Color.RED);

			// Get the location of the current cell
			x = this.getCol() * cellWidth;
			y = this.getRow() * cellHeight;

			// From the door direction set the dimensions and locations
			if (this.getDoorDirection() == DoorDirection.UP) {
				doorWidth = cellHeight / 5;
				doorLength = cellWidth;

				g.fillRect(x, y - doorWidth, doorLength, doorWidth);

			} else if (this.getDoorDirection() == DoorDirection.DOWN) {
				doorWidth = cellHeight / 5;
				doorLength = cellWidth;
				g.fillRect(x, y + cellHeight, doorLength, doorWidth);
			} else if (this.getDoorDirection() == DoorDirection.LEFT) {
				doorWidth = cellHeight;
				doorLength = cellWidth / 5;
				g.fillRect(x - doorLength, y, doorLength, doorWidth);

			} else {
				doorWidth = cellHeight;
				doorLength = cellWidth / 5;
				g.fillRect(x + cellWidth, y, doorLength, doorWidth);

			}

		}
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
