package clueGame;

import java.util.ArrayList;
import java.util.List;

// I'm gonna be honest, this is not a great singleton at all.
public class Board {

	/*
	 * variable and methods used for singleton pattern
	 */
	private static final Board INSTANCE = new Board();
	private String layout;
	private String setup;
	private List<List<BoardCell>> board;

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

		this.loadSetupConfig();

		this.loadLayoutConfig();

	}

	public void setConfigFiles(String layout, String setup) {
		// TODO Auto-generated method stub
		this.layout = layout;
		this.setup = setup;

	}

	public void loadSetupConfig() {
//		File set = new File("data/" + this.setup);
	}

	public void loadLayoutConfig() {
//		File lay = new File("data/" + this.setup);
//		Scanner reader = new Scanner(lay);
//
//		int rows = 0;
//		int cols = -1;
//
//		while (reader.hasNextLine()) {
//			String data = reader.nextLine();
//			String[] splitData = data.split(",");
//			if (cols == -1) {
//				cols = splitData.length;
//			} else if (splitData.length != cols) {
//				// Stub, put error message / throws BadConfigFormatException
//			}
//			ArrayList<BoardCell> r = new ArrayList<BoardCell>(cols);
//
//			for (String cell : splitData) {
//				r.add(new BoardCell());
//			}
//			this.board.add(r);
//		}
	}

	public Room getRoom(char c) {
		// TODO Auto-generated method stub
		return new Room();
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return 0;
	}

	public BoardCell getCell(int i, int j) {
		// TODO Auto-generated method stub
		return new BoardCell();
	}

	public Room getRoom(BoardCell cell) {
		// TODO Auto-generated method stub
		return null;
	}

}
