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

public class Board {

	/*
	 * variable and methods used for singleton pattern
	 */

	private int numRows;
	private int numCols = -1;
	private int numRooms = 0;

	private static Board INSTANCE = new Board();
	private String layout;
	private String setup;
	private List<List<BoardCell>> board;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> targets;
	private ArrayList<BoardCell> doorways;

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
		} catch (FileNotFoundException error) {
			System.out.println("File Not Found");

		} catch (BadConfigFormatException error) {
			System.out.println("Bad Config: Invalid Character in Setup");
			error.printStackTrace();
		}

		try {
			this.loadLayoutConfig();
		} catch (FileNotFoundException error) {
			System.out.println("File Not Found");

		} catch (BadConfigFormatException error) {
			System.out.println("Bad Config: Invalid Character in Layout");
			error.printStackTrace();
		}
		this.initializeAdjacencies();
	}

	/*
	 * set the config files in the object to whatever layout, string are.
	 */

	public void setConfigFiles(String layout, String setup) {
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

		File lay = new File("data/" + this.layout);
		Scanner reader = new Scanner(lay);

		this.doorways = new ArrayList<BoardCell>();

		int rowIndex = 0;
		int cols = 0;

		// Iterate through each row of the csv and thus each row of the board
		while (reader.hasNextLine()) {

			// Seperate out the columns delimeted by a csv
			String data = reader.nextLine();
			String[] splitData = data.split(",");

			cols = splitData.length;

			// If the number of Columns hasn't been set
			if (this.numCols == -1) {
				this.numCols = cols;
			} else if (cols != this.numCols) {
				// if the number of columns varies, then we have a bad configuration
				throw new BadConfigFormatException();
			}

			// create the array for the row
			ArrayList<BoardCell> row = new ArrayList<BoardCell>(cols);

			// iterate through the split data
			int colIndex = 0;
			for (String cell : splitData) {
				BoardCell currentCell = new BoardCell(rowIndex, colIndex);
				colIndex++;

				// if the room label is not valid throw an Exception
				if (!roomMap.containsKey(cell.charAt(0))) {
					throw new BadConfigFormatException();
				}
				// If the cell is a normal cell, just a label
				if (cell.length() == 1) {
					currentCell.setCellLabel(cell.charAt(0));

					// if the cell has some special operation
				} else if (cell.length() == 2) {
					currentCell.setCellLabel(cell.charAt(0));
					char specialOperation = cell.charAt(1);

					DoorDirection direction;
					// set the door direction
					if (specialOperation == '^') {
						direction = DoorDirection.UP;
						currentCell.setDoorDirection(direction);
					} else if (specialOperation == '>') {
						direction = DoorDirection.RIGHT;
						currentCell.setDoorDirection(direction);
					} else if (specialOperation == '<') {
						direction = DoorDirection.LEFT;
						currentCell.setDoorDirection(direction);
					} else if (specialOperation == 'v') {
						direction = DoorDirection.DOWN;
						currentCell.setDoorDirection(direction);
					}
					// set the room label
					else if (specialOperation == '#') {
						currentCell.setRoomLabel(true);
						// set the room center
					} else if (specialOperation == '*') {
						currentCell.setRoomCenter(true);
					} else if (roomMap.containsKey(specialOperation)) {
						// else if the value is another room, then it's a secret passage
						currentCell.setSecretPassage(specialOperation);
					} else {
						// Else there is an error and it's a bad config
						throw new BadConfigFormatException();
					}
				}
				row.add(currentCell);

				if (currentCell.isDoorway()) {
					this.doorways.add(currentCell);
				}

			}

			// add the row
			this.board.add(row);

			// increment the row
			rowIndex++;

		}
		// Close the file
		reader.close();
		// Set the number of rows
		this.numRows = rowIndex;
	}

	private void initializeAdjacencies() {

		ArrayList<BoardCell> secretPaths = new ArrayList<BoardCell>();

		// iterate through the gameboard
		for (int rowIndex = 0; rowIndex < this.numRows; rowIndex++) {
			for (int colIndex = 0; colIndex < this.numCols; colIndex++) {

				// get the boardcell
				BoardCell currentCell = this.board.get(rowIndex).get(colIndex);

				Room room = this.roomMap.get(currentCell.getCellLabel());

				// If the cell is not 'Unused' then it needs adjacencies.
				if (currentCell.getCellLabel() != 'X') {

					// if the room is the center, set it
					if (currentCell.getRoomCenter()) {
						room.setCenter(currentCell);
					} else if (currentCell.getRoomLabel()) { // if the room is the label, set it, room labels have no
																// adjacencies
						room.setLabelCell(currentCell);
					} else if (currentCell.getSecretPassage() != '\0') {
						secretPaths.add(currentCell);
					} else {

						// Check bounds for "normal cells"
						if (rowIndex - 1 >= 0) {
							BoardCell adj = this.board.get(rowIndex - 1).get(colIndex);
							this.processCell(currentCell, adj);
						}

						if (rowIndex + 1 < this.numRows) {
							BoardCell adj = this.board.get(rowIndex + 1).get(colIndex);
							this.processCell(currentCell, adj);
						}

						if (colIndex - 1 >= 0) {
							BoardCell adj = this.board.get(rowIndex).get(colIndex - 1);
							this.processCell(currentCell, adj);
						}

						if (colIndex + 1 < this.numCols) {
							BoardCell adj = this.board.get(rowIndex).get(colIndex + 1);
							this.processCell(currentCell, adj);
						}
					}
				}
			}
		}

		addSecretPassageAdjacencies(secretPaths);
	}

	private void addSecretPassageAdjacencies(ArrayList<BoardCell> secretPaths) {
		BoardCell centerCell;
		BoardCell secretCell;

		// Add secret cell adjacencies from secretPaths list
		for (BoardCell sp : secretPaths) {
			centerCell = roomMap.get(sp.getCellLabel()).getCenterCell();
			secretCell = roomMap.get(sp.getSecretPassage()).getCenterCell();
			centerCell.addAdjacency(secretCell);
			secretCell.addAdjacency(centerCell);
		}

		// Code for adding adjacencies from room center, secret path done at end.
		for (BoardCell doorway : this.doorways) {

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

	/*
	 * Return the adjacency list of the boardCell at (i, j)
	 */
	public Set<BoardCell> getAdjList(int i, int j) {
		return board.get(i).get(j).getAdjList();
	}

	/*
	 * get the targets stored in the Set of targets
	 */
	public Set<BoardCell> getTargets() {
		return this.targets;
	}

	/*
	 * Caller function for calcTargets(BoardCell startCell, int pathlength, int
	 * maxpath, Set<BoardCell> visited), but this one is shorter and more human
	 * readable.
	 */
	public void calcTargets(BoardCell startCell, int pathlength) {
		targets = new HashSet<BoardCell>();
		this.calcTargets(startCell, pathlength, pathlength, new HashSet<BoardCell>());
	}

	/*
	 * Calculate all the targets and add them to the adjacency list, this method is
	 * private because it is called by the public one above which is used as a
	 * "setup" function.
	 */
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

	public ArrayList<Player> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Player> getHumanPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Player> getCpuPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Card> getDeck() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Card> getSolution() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deal() {
		// TODO Auto-generated method stub

	}

}
