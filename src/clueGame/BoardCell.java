package clueGame;
import java.util.Set;

public class BoardCell {
	
	private char cellLabel;
	private int row;
	private int col;
	private DoorDirection doorDirection;
	private Boolean roomLabel = false;
	private Boolean roomCenter = false;
	private char secretPassage;
	private Set<BoardCell> adjList;
	
	public BoardCell(int row, int col) {
		this.row =  row;
		this.col = col;
		this.cellLabel = cellLabel;
		
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
		return adjList;
	}



	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}



	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}



	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isLabel() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return false;
	}

	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return 0;
	}

}
