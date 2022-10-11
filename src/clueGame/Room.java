package clueGame;

public class Room {
	private String name;
	private BoardCell center;
	private BoardCell labelCell;

	public Room(String name) {
		this.name = name;
	}

	public BoardCell getCenter() {
		return center;
	}

	public void setCenter(BoardCell center) {
		this.center = center;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public BoardCell getLabelCell() {
		// TODO Auto-generated method stub
		return this.labelCell;
	}

	public BoardCell getCenterCell() {
		// TODO Auto-generated method stub
		return this.center;
	}

}
