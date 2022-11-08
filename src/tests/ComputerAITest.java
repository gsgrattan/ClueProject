package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class ComputerAITest {
	ComputerPlayer testPlayer;
	Board board;

	List<List<BoardCell>> gameBoard;

	Set<BoardCell> targets;

	@BeforeAll
	void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize the board
		board.initialize();
		board.deal();

	}

	@Test
	void computerSuggestionTest() {
		ArrayList<Card> cards;
		Character playerRoomChar;
		Card playerRoom;

		Solution suggestion;
		testPlayer = new ComputerPlayer("testPlayer");

		// Get the board
		gameBoard = board.getBoard();

		// TEST to make sure a suggestion contains the room it was made in
		// Set the player's location to the center of the Parlor (2,2) with first index
		// at 0
		testPlayer.setLocation(gameBoard.get(2).get(2));
		playerRoomChar = testPlayer.getLocation().getCellLabel();
		playerRoom = board.getRoomMap().get(playerRoomChar).getRoomCard();

		suggestion = testPlayer.createSuggestion();

		// Check if the player's location is the same as the suggestion
		assertEquals(suggestion.getRoom(), playerRoom);

		// TEST for the playerCards
		// Get the Player cards and store them in an arraylist

		cards = new ArrayList(board.getPlayerCards());

		// All but the first two playerCards
		for (int i = 2; i < cards.size(); ++i) {
			testPlayer.updateSeen(cards.get(i));
		}
		// Since this is random (Two cards not seen) I will run multiple tests, and
		// assert that the number
		// of occurrences of each outcome is approximately the same

		int numTests = 100;

		int idxZeroSeen = 0;
		int idxOneSeen = 0;

		for (int i = 0; i < numTests; ++i) {
			// Make a suggestion
			suggestion = testPlayer.createSuggestion();

			// Get the Person for that suggestion
			Card suggestionPerson = suggestion.getPerson();
			// If the suggestion is the first card
			if (suggestionPerson.equals(cards.get(0))) {
				idxZeroSeen++;
			} else if (suggestionPerson.equals(cards.get(i))) {
				idxOneSeen++;
			}
			// if it is neither of these two, fail that MF
			else {
				fail();
			}
		}
		// Assert that the algorithm is approximately random between indicies 0 and 1
		assertTrue(java.lang.Math.abs(idxZeroSeen - idxOneSeen) < numTests / 2);

		// Now the only person the player hasn't seen is the player at index 0
		testPlayer.updateSeen(cards.get(1));
		suggestion = testPlayer.createSuggestion();

		// Check to see if it chooses the only card it hasn't seen
		assertEquals(suggestion.getPerson(), cards.get(0));

		// TEST for the weapon Cards

		// Get the weapon cards and store them in an arraylist
		cards = new ArrayList(board.getWeaponCards());

		// All but the first two playerCards
		for (int i = 2; i < cards.size(); ++i) {
			testPlayer.updateSeen(cards.get(i));
		}
		// Since this is random (Two cards not seen) I will run multiple tests, and
		// assert that the number
		// of occurrences of each outcome is approximately the same

		numTests = 100;

		idxZeroSeen = 0;
		idxOneSeen = 0;

		for (int i = 0; i < numTests; ++i) {
			// Make a suggestion
			suggestion = testPlayer.createSuggestion();

			// Get the weapon for that suggestion
			Card suggestionWeapon = suggestion.getWeapon();
			// If the suggestion is the first card
			if (suggestionWeapon.equals(cards.get(0))) {
				idxZeroSeen++;
			} else if (suggestionWeapon.equals(cards.get(i))) {
				idxOneSeen++;
			}
			// if it is neither of these two, fail that MF
			else {
				fail();
			}
		}
		// Assert that the algorithm is approximately random between indicies 0 and 1
		assertTrue(java.lang.Math.abs(idxZeroSeen - idxOneSeen) < numTests / 2);

		// Now the only person the player hasn't seen is the player at index 0
		testPlayer.updateSeen(cards.get(1));
		suggestion = testPlayer.createSuggestion();

		// Check to see if it chooses the only card it hasn't seen
		assertEquals(suggestion.getWeapon(), cards.get(0));

	}

	@Before
	void init() {
		// Create a target List that is just spaces
		BoardCell cell1 = new BoardCell(0, 0);
		BoardCell cell2 = new BoardCell(0, 1);

		targets.add(cell1);
		targets.add(cell2);

	}

	@Test
	void computerTargetTest() {
		testPlayer = new ComputerPlayer("testPlayer");
		// Two targets that are not rooms
		BoardCell cell0 = new BoardCell(0, 0);
		BoardCell cell1 = new BoardCell(0, 1);

		int numTests = 100;
		int num0 = 0;
		int num1 = 0;
		for (int i = 0; i < numTests; ++i) {
			BoardCell selected = testPlayer.selectTarget(targets);
			if (selected.getCol() == 0) {
				num0++;
			} else if (selected.getCol() == 1) {
				num1++;
			} else {
				fail();
			}

		}

		// Assert that it is choosing between the two non room Cells randomly
		assertTrue(java.lang.Math.abs(num0 - num1) < numTests / 2);

		// Check that it selects the room it hasn't seen
		// TODO: get this working with a room it has seen, and a room it hasnt

		BoardCell cell2 = new BoardCell(0, 2);
		cell2.setRoomLabel(true);
		targets.add(cell2);
		BoardCell selected = testPlayer.selectTarget(targets);
		// Check if they are the same room
		assertEquals(selected, cell2);

	}
}
