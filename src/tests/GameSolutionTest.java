package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		// config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize the board
		board.initialize();

		playerCards = board.getPlayerCards();
		weaponCards = board.getWeaponCards();
		roomCards = board.getRoomCards();
		players = board.getPlayers();

	}

	@Test
	void AccusationTests() {

		Solution trueSolution = board.getSolution();

		// Test if checkAccusation returns true when given the true solution
		assertTrue(board.checkAccusation(trueSolution));
		Solution wrongPerson = board.getSolution();
		for (Card person : playerCards) {
			if (person != trueSolution.getPerson()) {
				wrongPerson.setPerson(person);
				break;

			}
		}

		Solution wrongWeapon = board.getSolution();

		for (Card weapon : weaponCards) {
			if (weapon != trueSolution.getWeapon()) {
				wrongWeapon.setWeapon(weapon);
				break;
			}
		}

		Solution wrongRoom = board.getSolution();

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
		Player testPlayer = new ComputerPlayer("testPlayer");

		proof = testPlayer.disproveSuggestion(accusation);

		// The testplayer currently has an empty and so it should not be able to
		// disprove, thus it returns null
		// The proof should be Null

		assertNull(proof);

		testPlayer.updateHand(accusation.getPerson());

		proof = testPlayer.disproveSuggestion(accusation);

		// Assert that it returns the correct card
		assertEquals(proof, accusation.getPerson());

		// Now it has two cards in its hand
		testPlayer.updateHand(accusation.getWeapon());

		int numTests = 100;
		int numPeople = 0;
		int numWeapons = 0;
		int tol = 25;
		for (int i = 0; i < numTests; ++i) {
			proof = testPlayer.disproveSuggestion(accusation);
			if (proof.equals(accusation.getPerson())) {
				++numPeople;
			} else if (proof.equals(accusation.getWeapon())) {
				++numWeapons;
			}
		}
		// Check that it returns a random card if it has multiple disproving cards
		assertTrue(java.lang.Math.abs(numWeapons - numPeople) < numTests);
	}

	@Test
	void handleSuggestionTests() {

		// If nobody can disprive the suggestion null is returned
		Solution trueSolution = board.getSolution();
		assertNull(board.handleSuggestion(trueSolution));

	}

	@Test
	void computerSuggestionTests() {
		fail("Not yet implemented");
	}

}
