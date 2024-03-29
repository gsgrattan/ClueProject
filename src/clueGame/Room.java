package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Room {
	private String name;
	private BoardCell center;
	private BoardCell labelCell;
	private boolean isRoom = false;

	private ArrayList<BoardCell> cellList;

	private Card roomCard;

	/*
	 * Create the Room object, setting the room name to name.
	 */
	public Room(String name) {
		this.name = name;
		cellList = new ArrayList<BoardCell>();

	}

	// Draws the label of the room, Potentially the room's special art if we're
	// feeling fancy
	public void draw(Graphics g, int cellWidth, int cellHeight) {
		// if it is actually a room and not a space or walkway
		if (this.isRoom) {
			// draw the labels
			int x = cellWidth * labelCell.getCol();
			int y = cellHeight * labelCell.getRow();
			g.setColor(Color.pink);
			g.drawString(name, x, y);
		}

	}

	/*
	 * Set the center cell of the room
	 */
	public void setCenter(BoardCell center) {
		this.center = center;
		this.center.setCard(this.roomCard);
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

	public Card getRoomCard() {
		return roomCard;
	}

	public void setRoomCard(Card roomCard) {
		this.roomCard = roomCard;
	}

	public void addCell(BoardCell cell) {
		this.cellList.add(cell);
	}

	public ArrayList<BoardCell> getCellList() {
		return this.cellList;

	}

}
