package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import clueGame.Board;

class GameSetupTests {
	private static Board board;

	@Before
	public static void setUp() {
		// Create the one and only instance of the board class
		board = Board.getInstance();
		// Set the Config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize the board
		board.initialize();

	}

	@Test
	public void testPlayers() {
		// Check the number of players
		Assert.assertEquals(Board.NUM_PLAYERS, 6);

	}

}
