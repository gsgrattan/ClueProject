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
	}

	// this method returns the only Board
	public static Board getInstance() {
		return INSTANCE;
	}

	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize() {
		this.board = new ArrayList<List<BoardCell>>();

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

	public void setConfigFiles(String layout, String setup) {
		// TODO Auto-generated method stub
		this.layout = layout;
		this.setup = setup;

	}

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
				}

				Room r = new Room(splitData[1]);
				roomMap.put(splitData[2].charAt(0), r);
			}
		}
	}

	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		File lay = new File("data/" + this.layout);
		Scanner reader = new Scanner(lay);

		int rows = 0;
		int cols = 0;

		while (reader.hasNextLine()) {

			String data = reader.nextLine();
			String[] splitData = data.split(",");

			if (cols == -1) {
				cols = splitData.length;
			} else if (splitData.length != cols) {
				// Stub, put error message / throws BadConfigFormatException
			}
			ArrayList<BoardCell> r = new ArrayList<BoardCell>(cols);

			for (String cell : splitData) {
				BoardCell b = new BoardCell(rows, cols);

				if (cell.length() == 1) {

					b.setCellLabel(cell.charAt(0));
				} else if (cell.length() == 2) {
					b.setCellLabel(cell.charAt(0));
					char specialOperation = cell.charAt(1);

					DoorDirection direction;

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

					else if (specialOperation == '#') {
						b.setRoomLabel(true);
					} else if (specialOperation == '*') {
						b.setRoomCenter(true);
					} else {
						if (roomMap.containsKey(specialOperation)) {
							b.setSecretPassage(specialOperation);
						} else {
							throw new BadConfigFormatException();
						}

					}
				}
				r.add(b);

				cols++;

			}
			this.board.add(r);

			rows++;

			if (numCols != -1) {
				if (this.numCols != cols) {
					throw new BadConfigFormatException();
				}
			} else {
				this.numCols = cols;
			}

			cols = 0;
		}
		this.numRows = rows;

		for (int i = 0; i < this.numRows; i++) {
			for (int j = 0; j < this.numCols; j++) {

				BoardCell b = this.board.get(i).get(j);

				Room r = this.roomMap.get(b.getCellLabel());

				if (b.getRoomCenter()) {
					r.setCenter(b);
				}

				if (b.getRoomLabel()) {
					r.setLabelCell(b);
				}

//				if (i - 1 >= 0) {
//					BoardCell adj = this.board.get(i - 1).get(j);
//					this.board.get(i).get(j).addAdjacency(adj);
//				}
//
//				if (i + 1 < rows) {
//					BoardCell adj = this.board.get(i + 1).get(j);
//					this.board.get(i).get(j).addAdjacency(adj);
//				}
//
//				if (j - 1 >= 0) {
//					BoardCell adj = this.board.get(i).get(j - 1);
//					this.board.get(i).get(j).addAdjacency(adj);
//				}
//
//				if (j + 1 < rows) {
//					BoardCell adj = this.board.get(i).get(j + 1);
//					this.board.get(i).get(j).addAdjacency(adj);
//				}
			}
		}

	}

	public Room getRoom(char c) {
		return roomMap.get(c);

	}

	public int getNumRows() {

		return this.numRows;
	}

	public int getNumColumns() {
		return this.numCols;
	}

	public int getNumRooms() {
		return this.numRooms;
	}

	public BoardCell getCell(int row, int col) {
		return board.get(row).get(col);
	}

	public Room getRoom(BoardCell cell) {
		char label = cell.getCellLabel();
		return this.roomMap.get(label);
	}

}
