package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

// I'm gonna be honest, this is not a great singleton at all.
public class Board {

	/*
	 * variable and methods used for singleton pattern
	 */

	// TODO: Switch rows and columns becuase they are backwards right now.
	private int numRows;
	private int numCols = -1;
	private int numRooms = 0;

	private static Board INSTANCE = new Board();
	private String layout;
	private String setup;
	private List<List<BoardCell>> board;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> targets;

	// constructor is private to ensure only one can be created
	private Board() {
		super();
		this.board = new ArrayList<List<BoardCell>>();
	}

	// this method returns the only Board
	public static Board getInstance() {
		return INSTANCE;
	}

	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize() {
		INSTANCE = new Board();
		this.board = new ArrayList<List<BoardCell>>();
		this.roomMap = new HashMap<Character, Room>();
		numCols = -1;
		numRooms = 0;

		try {
			this.loadSetupConfig();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");

		} catch (BadConfigFormatException e) {
			System.out.println("Bad Config: Invalid Character in Setup");
			e.printStackTrace();
		}

		try {
			this.loadLayoutConfig();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");

		} catch (BadConfigFormatException e) {
			System.out.println("Bad Config: Invalid Character in Layout");
			e.printStackTrace();
		}
	}

	/*
	 * set the config files in the object to whatever layout, string are.
	 */

	public void setConfigFiles(String layout, String setup) {
		// TODO Auto-generated method stub
		this.layout = layout;
		this.setup = setup;

	}

	/*
	 * Load the setup config file into the roomMap.
	 */

	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
		roomMap = new HashMap();
		File set = new File("data/" + this.setup);
		Scanner reader = new Scanner(set);
		while (reader.hasNextLine()) {
			String data = reader.nextLine();

			if (!data.substring(0, 2).equals("//") && !data.equals("\n")) {

				String[] splitData = data.split(", ");

				if (splitData[0].equals("Room")) {
					this.numRooms++;
				} else if (!splitData[0].equals("Space")) {
					throw new BadConfigFormatException();
				}

				Room r = new Room(splitData[1]);

				if (splitData[0].equals("Room")) {
					r.setIsRoom(true);
				}

				roomMap.put(splitData[2].charAt(0), r);
			}
		}

		reader.close();
	}

	/*
	 * Load the playing board.
	 */
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {

		// System.out.println("\n\n\nSTART OF OUTPUT:");

		File lay = new File("data/" + this.layout);
		Scanner reader = new Scanner(lay);

		ArrayList<BoardCell> doorways = new ArrayList<BoardCell>();

		int rows = 0;
		int cols = 0;

		// Iterate through each row of the csv and thus each row of the board
		while (reader.hasNextLine()) {

			// Seperate out the columns delimeted by a csv
			String data = reader.nextLine();
			String[] splitData = data.split(",");

			cols = splitData.length;

			// Set the number of columns of the forst pass through
			if (numCols == -1) {
				numCols = cols;
			}

			// create the array for the row
			ArrayList<BoardCell> r = new ArrayList<BoardCell>(cols);

			// iterate through the split data
			int col = 0;
			for (String cell : splitData) {
				BoardCell b = new BoardCell(rows, col);
				col++;

				// if the room label is not valid throw an Exception
				if (!roomMap.containsKey(cell.charAt(0))) {
					throw new BadConfigFormatException();
				}

				// If the cell is a normal cell, just a label
				if (cell.length() == 1) {

					b.setCellLabel(cell.charAt(0));

					// if the cell has some spectial operation
				} else if (cell.length() == 2) {

					// System.out.println(cell);

					// TODO: reformat these logical statements to clean up the code, potentially
					// adding a helper function into the BoardCell class.

					b.setCellLabel(cell.charAt(0));
					char specialOperation = cell.charAt(1);

					DoorDirection direction;
					// set the door direction
					if (specialOperation == '^') {
						direction = DoorDirection.UP;
						b.setDoorDirection(direction);
						// doorways.add(b);
					} else if (specialOperation == '>') {
						direction = DoorDirection.RIGHT;
						b.setDoorDirection(direction);
						// doorways.add(b);
					} else if (specialOperation == '<') {
						direction = DoorDirection.LEFT;
						b.setDoorDirection(direction);
						// doorways.add(b);
					} else if (specialOperation == 'v') {
						direction = DoorDirection.DOWN;
						b.setDoorDirection(direction);
						// doorways.add(b);
					}
					// set the room label
					else if (specialOperation == '#') {
						b.setRoomLabel(true);
						// set the room center
					} else if (specialOperation == '*') {
						b.setRoomCenter(true);
					} else {
						// else it's a secret passage or an error
						if (roomMap.containsKey(specialOperation)) {
							b.setSecretPassage(specialOperation);
						} else {
							throw new BadConfigFormatException();
						}
					}
				}
				r.add(b);

				// System.out.println(b.getCellLabel());

				if (b.isDoorway()) {
					// System.out.println("This is a doorway. Row: " + b.getRow() + ", Col: " +
					// b.getCol());

					doorways.add(b);
				}

			}

			// if the board size has been set/ initialized
			if (this.board.size() != 0) {
				// assert that it has the correct number of columns, if it doesn't, throw an
				// exception
				if (cols != this.numCols) {
					throw new BadConfigFormatException();
				}
			}
			// add the row
			this.board.add(r);

			// increment the row
			rows++;

			// reset the column
			cols = 0;
		}

		reader.close();

		this.numRows = rows;

		ArrayList<BoardCell> secretPaths = new ArrayList<BoardCell>();

		// iterate through the gameboard
		for (int i = 0; i < this.numRows; i++) {
			for (int j = 0; j < this.numCols; j++) {

				// get the boardcell
				BoardCell b = this.board.get(i).get(j);

				Room r = this.roomMap.get(b.getCellLabel());

				// If the cell is not 'Unused' then it needs adjacencies.
				if (b.getCellLabel() != 'X') {

					// if the room is the center, set it
					if (b.getRoomCenter()) {
						r.setCenter(b);
					} else if (b.getRoomLabel()) { // if the room is the label, set it, room labels have no adjacencies
						r.setLabelCell(b);
					} else if (b.getSecretPassage() != '\0') {
						secretPaths.add(b);
					} else {

						// Check bounds for "normal cells"
						if (i - 1 >= 0) {
							BoardCell adj = this.board.get(i - 1).get(j);
							this.processCell(b, adj);
						}

						if (i + 1 < this.numRows) {
							BoardCell adj = this.board.get(i + 1).get(j);
							this.processCell(b, adj);
						}

						if (j - 1 >= 0) {
							BoardCell adj = this.board.get(i).get(j - 1);
							this.processCell(b, adj);
						}

						if (j + 1 < this.numCols) {
							BoardCell adj = this.board.get(i).get(j + 1);
							this.processCell(b, adj);
						}
					}
				}
			}
		}

		BoardCell cc;
		BoardCell sc;

		// Add secret cell adjacencies from secretPaths list
		for (BoardCell sp : secretPaths) {
			cc = roomMap.get(sp.getCellLabel()).getCenter();
			sc = roomMap.get(sp.getSecretPassage()).getCenter();
			// System.out.println("PAIR IS: " + cc.getCellLabel() + ", " +
			// sc.getCellLabel());
			cc.addAdjacency(sc);
			sc.addAdjacency(cc);
		}

		// Code for adding adjacencies from room center, secret path done at end.
		for (BoardCell doorway : doorways) {

			BoardCell checkedCell = new BoardCell(-1, -1);

			if (doorway.getDoorDirection() == DoorDirection.UP) {
				checkedCell = this.board.get(doorway.getRow() - 1).get(doorway.getCol());
			}

			else if (doorway.getDoorDirection() == DoorDirection.DOWN) {
				checkedCell = this.board.get(doorway.getRow() + 1).get(doorway.getCol());
			}

			else if (doorway.getDoorDirection() == DoorDirection.LEFT) {
				checkedCell = this.board.get(doorway.getRow()).get(doorway.getCol() - 1);
			}

			else if (doorway.getDoorDirection() == DoorDirection.RIGHT) {
				checkedCell = this.board.get(doorway.getRow()).get(doorway.getCol() + 1);
			}

			BoardCell roomCell = roomMap.get(checkedCell.getCellLabel()).getCenterCell();

			if (checkedCell.getCellLabel() == roomCell.getCellLabel()) {
				roomCell.addAdjacency(doorway);
				doorway.addAdjacency(roomCell);
			}

		}
	}

	/*
	 * Helper function for adding cell adjancies.
	 */
	private void processCell(BoardCell t, BoardCell adj) {
		if (adj.getCellLabel() != 'X' && !roomMap.get(adj.getCellLabel()).getIsRoom()) {
			t.addAdjacency(adj);
		}
	}

	/*
	 * Get the room
	 */
	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	/*
	 * Get the number of rows in the file
	 */
	public int getNumRows() {
		return this.numRows;
	}

	/*
	 * Get the number of columns in the board
	 */
	public int getNumColumns() {
		return this.numCols;
	}

	/*
	 * get the number of rooms in the layout
	 */
	public int getNumRooms() {
		return this.numRooms;
	}

	/*
	 * get a specific cell specified by (row, column)
	 */

	public BoardCell getCell(int row, int col) {
		return board.get(row).get(col);
	}

	/*
	 * Get the room label of a cell
	 */
	public Room getRoom(BoardCell cell) {
		char label = cell.getCellLabel();
		return this.roomMap.get(label);
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return board.get(i).get(j).getAdjList();
	}

	public Set<BoardCell> getTargets() {
		return this.targets;
	}

	public void calcTargets(BoardCell startCell, int pathlength) {

//		System.out.println("\nNew targets generated\n");

		targets = new HashSet<BoardCell>();
		this.calcTargets(startCell, pathlength, pathlength, new HashSet<BoardCell>());

	}

	private void calcTargets(BoardCell startCell, int pathlength, int maxpath, Set<BoardCell> visited) {
		if (visited.isEmpty()) {
			visited.add(startCell);
		}

		if (pathlength == 0 || (startCell.isRoomCenter() && maxpath != pathlength)) {
			targets.add(startCell);

		} else {
			for (BoardCell cell : startCell.getAdjList()) {

				if ((cell.isRoomCenter() || !cell.getOccupied()) && !visited.contains(cell)) {

					visited.add(cell);
					calcTargets(cell, pathlength - 1, maxpath, visited);
					visited.remove(cell);
				}
			}
		}
	}

}
