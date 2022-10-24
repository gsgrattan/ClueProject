package clueGame;

public class Room {
	private String name;
	private BoardCell center;
	private BoardCell labelCell;
	private boolean isRoom = false;

	/*
	 * Create the Room object, setting the room name to name.
	 */
	public Room(String name) {
		this.name = name;
	}

	/*
	 * Get the center cell of the room
	 */
	public BoardCell getCenter() {
		return center;
	}

	/*
	 * Set the center cell of the room
	 */
	public void setCenter(BoardCell center) {
		this.center = center;
	}

	/*
	 * Set the label cell of the room
	 */
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}

	/*
	 * Get the name of the room
	 */
	public String getName() {
		return this.name;
	}

	/*
	 * Set the center cell of the room
	 */
	public BoardCell getLabelCell() {
		return this.labelCell;
	}

	/*
	 * Get the center cell of the room
	 */
	public BoardCell getCenterCell() {
		return this.center;
	}

	/*
	 * Get if the room is actually a room because walkways and unused cells are
	 * "rooms" but not actually.
	 */
	public boolean getIsRoom() {
		return isRoom;
	}

	/*
	 * Set if the room is actually a room because walkways and unused cells are
	 * "rooms" but not actually.
	 */
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}

}
