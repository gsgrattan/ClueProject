package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// I'm gonna be honest, this is not a great singleton at all.
public class Board {

	/*
	 * variable and methods used for singleton pattern
	 */

	// TODO: Switch rows and columns becuase they are backwards right now.
	private int numRows;
	private int numCols = -1;
	private int numRooms = 0;

	private static final Board INSTANCE = new Board();
	private String layout;
	private String setup;
	private List<List<BoardCell>> board;
	private Map<Character, Room> roomMap;

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
				roomMap.put(splitData[2].charAt(0), r);
			}
		}
	}

	/*
	 * Load the playing board.
	 */
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		File lay = new File("data/" + this.layout);
		Scanner reader = new Scanner(lay);

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
			for (String cell : splitData) {
				BoardCell b = new BoardCell(rows, cols);

				// if the room label is not valid throw an Exception
				if (!roomMap.containsKey(cell.charAt(0))) {
					throw new BadConfigFormatException();
				}

				// If the cell is a normal cell, just a label
				if (cell.length() == 1) {

					b.setCellLabel(cell.charAt(0));

					// if the cell has some spectial operation
				} else if (cell.length() == 2) {
					b.setCellLabel(cell.charAt(0));
					char specialOperation = cell.charAt(1);

					DoorDirection direction;
					// set the door direction
					if (specialOperation == '^') {
						direction = DoorDirection.UP;
						b.setDoorDirection(direction);
					} else if (specialOperation == '>') {
						direction = DoorDirection.RIGHT;
						b.setDoorDirection(direction);

					} else if (specialOperation == '<') {
						direction = DoorDirection.LEFT;
						b.setDoorDirection(direction);
					} else if (specialOperation == 'v') {
						direction = DoorDirection.DOWN;
						b.setDoorDirection(direction);
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
		this.numRows = rows;

		// iterate throught he gameboard
		for (int i = 0; i < this.numRows; i++) {
			for (int j = 0; j < this.numCols; j++) {
				// get the boardcell
				BoardCell b = this.board.get(i).get(j);

				Room r = this.roomMap.get(b.getCellLabel());

				// if the room is the center, set it so
				if (b.getRoomCenter()) {
					r.setCenter(b);
				}

				// if the room is the label, set it so
				if (b.getRoomLabel()) {
					r.setLabelCell(b);
				}

//		
			}
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

}
