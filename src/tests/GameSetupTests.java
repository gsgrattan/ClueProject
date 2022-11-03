package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameSetupTests {

	private static final int NUM_HUMAN = 1;
	private static final int NUM_CPU = 5;
	private static final int NUM_PLAYERS = NUM_HUMAN + NUM_CPU;

	private static final int NUM_ROOMS = 9;
	private static final int NUM_WEAPONS = 6;
	private static final int DECK_SIZE = NUM_PLAYERS + NUM_ROOMS + NUM_WEAPONS;

	private static Board board;

	@Before
	public void setUp() {
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
		Assert.assertEquals(board.getPlayers().size(), NUM_PLAYERS);
		// Check the number of human players
		Assert.assertEquals(board.getHumanPlayers().size(), NUM_HUMAN);
		// Check the number of computer players
		Assert.assertEquals(board.getCpuPlayers().size(), NUM_CPU);

		// Check the types of human players
		for (var player : board.getHumanPlayers()) {
			assertTrue(player instanceof HumanPlayer);
		}
		// Check the types of computer players
		for (var player : board.getCpuPlayers()) {
			assertTrue(player instanceof ComputerPlayer);

		}

	}

	@Test

	// below I assume that we implement the deck as a set
	public void testDeck() {
		Assert.assertEquals(board.getDeck().size(), DECK_SIZE);
		int numRooms = 0;
		int numPlayers = 0;
		int numWeapons = 0;

		for (Card card : board.getDeck()) {
			// Check that the type is a valid type
			assertTrue(card.getCardType() instanceof CardType);
			// if that passes, this will hold, then we then check the different types of
			// cards that make up the deck
			CardType type = card.getCardType();
			if (type.equals(CardType.PERSON)) {
				numPlayers++;
			} else if (type.equals(CardType.ROOM)) {
				numRooms++;
			} else if (type.equals(CardType.WEAPON)) {
				numWeapons++;

			}
			// if it's none of the above, it's an automatic failure
			else {
				fail();
			}
		}

		// Check that the deck has the proper number of each type of card
		assertEquals(numRooms, NUM_ROOMS);
		assertEquals(numPlayers, NUM_PLAYERS);
		assertEquals(numWeapons, NUM_WEAPONS);

	}

	@Test
	public void testDeal() {
		board.deal();
		ArrayList<Player> players = board.getPlayers();

		Solution solution = board.getSolution();

		// Check the solution size and card types
		assertEquals(solution.size(), 3);
		assertEquals(CardType.PERSON, solution.getPerp().getCardType());
		assertEquals(CardType.WEAPON, solution.getWeapon().getCardType());
		assertEquals( CardType.ROOM, solution.getPlace().getCardType());

		// Check the player cards

		// dividend = quotient*divisor + remainder
		// thus we should expect the players to have either numPlayerCards or
		// numPlayerCards + 1
		int numPlayerCards = DECK_SIZE - 3 / NUM_PLAYERS;

		// Iterate through the players
		for (Player player : board.getPlayers()) {
			boolean result = ((player.getHandSize() == numPlayerCards) || (player.getHandSize() == numPlayerCards + 1));
			// Assert that their hand has either numPlayerCards or numPlayerCards + 1
			assertTrue(result);

		}

	}
}
