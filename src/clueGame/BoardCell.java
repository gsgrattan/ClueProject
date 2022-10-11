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

	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		this.adjacencyList = new HashSet<BoardCell>();

	}

	public char getCellLabel() {
		return cellLabel;
	}

	public void setCellLabel(char cellLabel) {
		this.cellLabel = cellLabel;
	}

	public Boolean getRoomLabel() {
		return roomLabel;
	}

	public void setRoomLabel(Boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	public Boolean getRoomCenter() {
		return roomCenter;
	}

	public void setRoomCenter(Boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	public Set<BoardCell> getAdjList() {
		return adjacencyList;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
		this.isDoorway = true;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return this.isDoorway;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return this.doorDirection;
	}

	public boolean isLabel() {
		// TODO Auto-generated method stub
		return roomLabel;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return roomCenter;
	}

	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return secretPassage;
	}

	public void addAdjacency(BoardCell adj) {
		this.adjacencyList.add(adj);
	}

}
