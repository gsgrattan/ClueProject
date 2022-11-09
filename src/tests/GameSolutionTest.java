package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

class GameSolutionTest {
	private static Board board;

	private static ArrayList<Player> players;

	private Set<Card> deck;

	private static Set<Card> playerCards;
	private static Set<Card> weaponCards;
	private static Set<Card> roomCards;

	private static Solution trueSolution;

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		// config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize the board
		board.initialize();

		board.deal();

		playerCards = board.getPlayerCards();
		weaponCards = board.getWeaponCards();
		roomCards = board.getRoomCards();
		players = board.getPlayers();

		trueSolution = board.getSolution();
	}

	@Test
	void AccusationTests() {

		// Test if checkAccusation returns true when given the true solution
		assertTrue(board.checkAccusation(trueSolution));

		// Verify that these are not modifying board.trueSOlution();
		Solution wrongPerson = new Solution(trueSolution);
		for (Card person : playerCards) {
			if (person != trueSolution.getPerson()) {
				wrongPerson.setPerson(person);
				break;

			}
		}

		Solution wrongWeapon = new Solution(trueSolution);

		for (Card weapon : weaponCards) {
			if (weapon != trueSolution.getWeapon()) {
				wrongWeapon.setWeapon(weapon);
				break;
			}
		}

		Solution wrongRoom = new Solution(trueSolution);

		for (Card room : roomCards) {
			if (room != trueSolution.getRoom()) {
				wrongRoom.setRoom(room);
				break;
			}
		}

		// Check that checlAccusation returns false if others are flase
		assertFalse(board.checkAccusation(wrongPerson));
		assertFalse(board.checkAccusation(wrongWeapon));
		assertFalse(board.checkAccusation(wrongRoom));
	}

	@Test
	void disproveSuggestionTests() {

		Card proof;

		Solution accusation = board.getSolution();
		Player testPlayer0 = new ComputerPlayer("testPlayer0", board);

		Player testPlayer1 = new ComputerPlayer("testPlayer1", board);

		proof = testPlayer0.disproveSuggestion(accusation, testPlayer1);

		// The testplayer currently has an empty and so it should not be able to
		// disprove, thus it returns null
		// The proof should be Null

		assertNull(proof);

		testPlayer0.updateHand(accusation.getPerson());

		proof = testPlayer0.disproveSuggestion(accusation, testPlayer1);

		// Assert that it returns the correct card
		assertEquals(proof, accusation.getPerson());

		// Now it has two cards in its hand
		testPlayer0.updateHand(accusation.getWeapon());

		int numTests = 100;
		int numPeople = 0;
		int numWeapons = 0;
		for (int i = 0; i < numTests; ++i) {
			proof = testPlayer0.disproveSuggestion(accusation, testPlayer1);
			if (proof.equals(accusation.getPerson())) {
				++numPeople;
			} else if (proof.equals(accusation.getWeapon())) {
				++numWeapons;
			}
		}
		// Check that it returns a random card if it has multiple disproving cards
		assertTrue(java.lang.Math.abs(numWeapons - numPeople) < numTests / 2);
	}

	@Test
	void handleSuggestionTests() {

		// If nobody can disprove the suggestion null is returned
		assertNull(board.handleSuggestion(trueSolution, players.get(0)));

		// HumanPlayer is always at the first index
		// have the Human Player have the card for the person (Not Realistic in game,
		players.get(0).updateHand(trueSolution.getPerson());

		// Check that the player will not disprove their own suggestion
		assertNull(board.handleSuggestion(trueSolution, players.get(0)));

		// Assert that the Player will return the correct card to disprove the
		// suggestion
		assertEquals(board.handleSuggestion(trueSolution, players.get(1)), trueSolution.getPerson());

		// Have another player
		players.get(2).updateHand(trueSolution.getWeapon());

		// Check that the player order is obeyed, i.e. if player 2 makes a suggestion
		// and players 3 and 1 can disprove it, player 3 gets to disprove rather than 1
		assertEquals(board.handleSuggestion(trueSolution, players.get(1)), trueSolution.getWeapon());

		// but if player 4 makes the accusation, player 1 is the one bale to disprove
		assertEquals(board.handleSuggestion(trueSolution, players.get(3)), trueSolution.getPerson());

	}

}
