package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and
	// then do all the tests.
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load config files
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are Salmon on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms() {
		// we want to test a couple of different rooms.
		// First, the study that only has a single door but a secret room

		Set<BoardCell> testList = board.getAdjList(22, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(18, 2)));
		assertTrue(testList.contains(board.getCell(2, 21)));

	}

	// Ensure door locations include their rooms and also additional walkways
	// These cells are Salmon on the planning spreadsheet
	@Test
	public void testAdjacencyDoor() {
		Set<BoardCell> testList = board.getAdjList(18, 14);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(14, 2)));
		assertTrue(testList.contains(board.getCell(11, 2)));
	}

	// Test a variety of walkway scenarios
	// These tests are Lime Green on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways() {
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(18, 0);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(18, 1)));

		// Test near a door but not adjacent
		testList = board.getAdjList(9, 16);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(8, 16)));
		assertTrue(testList.contains(board.getCell(10, 16)));
		assertTrue(testList.contains(board.getCell(9, 17)));

		// Test adjacent to walkways
		testList = board.getAdjList(5, 6);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(5, 5)));
		assertTrue(testList.contains(board.getCell(5, 7)));
		assertTrue(testList.contains(board.getCell(6, 6)));
		assertTrue(testList.contains(board.getCell(4, 6)));

	}

	// Tests out of room center, 1, 3 and 4
	// These are Pink on the planning spreadsheet
	@Test
	public void testTargetsInBathroom() {
		// test a roll of 1
		board.calcTargets(board.getCell(12, 21), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(7, 19)));
		assertTrue(targets.contains(board.getCell(13, 17)));

		// test a roll of 3
		board.calcTargets(board.getCell(12, 21), 3);
		targets = board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(12, 16)));
		assertTrue(targets.contains(board.getCell(7, 21)));
		assertTrue(targets.contains(board.getCell(7, 17)));

		// test a roll of 4
		board.calcTargets(board.getCell(12, 21), 4);
		targets = board.getTargets();
		assertEquals(17, targets.size());
		assertTrue(targets.contains(board.getCell(7, 22)));
		assertTrue(targets.contains(board.getCell(7, 20)));
		assertTrue(targets.contains(board.getCell(8, 17)));
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(7, 10), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());

		assertTrue(targets.contains(board.getCell(4, 12)));
		assertTrue(targets.contains(board.getCell(7, 9)));
		assertTrue(targets.contains(board.getCell(7, 11)));

		// test a roll of 3
		board.calcTargets(board.getCell(7, 10), 3);
		targets = board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(4, 12)));
		assertTrue(targets.contains(board.getCell(7, 13)));
		assertTrue(targets.contains(board.getCell(7, 7)));
		assertTrue(targets.contains(board.getCell(6, 8)));
		assertTrue(targets.contains(board.getCell(8, 8)));
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(9, 7), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(9, 8)));
		assertTrue(targets.contains(board.getCell(8, 7)));

		// test a roll of 3
		board.calcTargets(board.getCell(7, 7), 3);
		targets = board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(8, 7)));
		assertTrue(targets.contains(board.getCell(7, 10)));
		assertTrue(targets.contains(board.getCell(12, 7)));

	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(24, 7), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(24, 8)));
		assertTrue(targets.contains(board.getCell(23, 7)));

		// test a roll of 3
		board.calcTargets(board.getCell(24, 7), 3);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(24, 8)));
		assertTrue(targets.contains(board.getCell(22, 8)));
		assertTrue(targets.contains(board.getCell(21, 7)));

	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 3 blocked 2 left
		board.getCell(18, 17).setOccupied(true);
		board.calcTargets(board.getCell(18, 19), 3);
		board.getCell(20, 20).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(18, 22)));
		assertTrue(targets.contains(board.getCell(18, 20)));
		assertTrue(targets.contains(board.getCell(18, 18)));
		assertFalse(targets.contains(board.getCell(18, 17)));

		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(22, 20).setOccupied(true);
		board.calcTargets(board.getCell(18, 19), 3);
		board.getCell(22, 20).setOccupied(false);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(22, 20)));

		// check leaving a room with a blocked doorway
		board.getCell(18, 21).setOccupied(true);
		board.calcTargets(board.getCell(22, 20), 3);
		board.getCell(18, 21).setOccupied(false);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(18, 19)));
		assertTrue(targets.contains(board.getCell(18, 23)));
		assertTrue(targets.contains(board.getCell(17, 20)));

	}
}
