package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class FileInitTests {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 10;
	public static final int NUM_ROWS = 24;
	public static final int NUM_COLUMNS = 25;

	// NOTE: I made Board static because I only want to set it up one
	// time (using @BeforeAll), no need to do setup before each test.
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

	@Test
	public void testRoomLabels() {
		// Test to make sure number of rooms is correct
		assertEquals(LEGEND_SIZE, board.getNumRooms());

		// To ensure data is correctly loaded, test retrieving all rooms
		assertEquals("Parlor", board.getRoom('P').getName());
		assertEquals("Kitchen", board.getRoom('K').getName());
		assertEquals("Mud Room", board.getRoom('M').getName());
		assertEquals("Library", board.getRoom('L').getName());
		assertEquals("Theater", board.getRoom('T').getName());
		assertEquals("Bedroom", board.getRoom('B').getName());
		assertEquals("Observatory", board.getRoom('O').getName());
		assertEquals("Bathroom", board.getRoom('A').getName());
		assertEquals("Office", board.getRoom('F').getName());
	}

	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}

	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		BoardCell cell = board.getCell(6, 16);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(4, 6);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(13, 17);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(17, 11);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());

		// Test that walkways are not doors
		cell = board.getCell(6, 7);
		assertFalse(cell.isDoorway());
	}

	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(14, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRooms() {
		// just test a standard room location
		BoardCell cell = board.getCell(2, 3);
		Room room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Parlor");
		assertFalse(cell.isLabel());
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isDoorway());

		// this is a label cell to test
		cell = board.getCell(3, 12);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Kitchen");
		assertTrue(cell.isLabel());
		assertTrue(room.getLabelCell() == cell);

		// this is a room center cell to test
		cell = board.getCell(24, 12);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Observatory");
		assertTrue(cell.isRoomCenter());
		assertTrue(room.getCenterCell() == cell);

		// this is a secret passage test
		cell = board.getCell(24, 24);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Office");
		assertTrue(cell.getSecretPassage() == 'P');

		// test a walkway
		cell = board.getCell(16, 7);
		room = board.getRoom(cell);
		// Note for our purposes, walkways and closets are rooms
		assertTrue(room != null);
		assertEquals(room.getName(), "Walkway");
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isLabel());

		// test an unused cell
		cell = board.getCell(11, 11);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Unused");
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isLabel());

	}
}
