package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class ComputerAITest {
	ComputerPlayer testPlayer;
	private static Board board;

	Set<BoardCell> targets;

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize the board
		board.initialize();
		board.deal();

	}

	@Test
	public void computerSuggestionTest() {
		ArrayList<Card> cards;
		Character playerRoomChar;
		Card playerRoom;

		Solution suggestion;
		testPlayer = new ComputerPlayer("testPlayer", board);

		// Get the board

		// TEST to make sure a suggestion contains the room it was made in
		// Set the player's location to the center of the Parlor (2,2) with first index
		// at 0
		testPlayer.setLocation(board.getCell(2, 2));
		playerRoomChar = testPlayer.getLocation().getCellLabel();
		playerRoom = board.getRoomMap().get(playerRoomChar).getRoomCard();

		suggestion = testPlayer.createSuggestion();

		// Check if the player's location is the same as the suggestion
		assertEquals(playerRoom, suggestion.getRoom());

		// TEST for the playerCards
		// Get the Player cards and store them in an array list

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
			} else if (suggestionPerson.equals(cards.get(1))) {
				idxOneSeen++;
			}
			// if it is neither of these two, fail
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

		// Get the weapon cards and store them in an array list
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
			} else if (suggestionWeapon.equals(cards.get(1))) {
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

	@Test
	public void computerTargetTest() {
		// Create a target List that is just spaces
		BoardCell cell0 = new BoardCell(0, 0);
		BoardCell cell1 = new BoardCell(0, 1);
		targets = new HashSet<BoardCell>();

		targets.add(cell0);
		targets.add(cell1);

		Card room1 = new Card("testCard1", CardType.ROOM);
		Card room2 = new Card("testCard2", CardType.ROOM);

		BoardCell cell2 = new BoardCell(0, 2);

		testPlayer = new ComputerPlayer("testPlayer", board);
		// Two targets that are not rooms

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

		// Remove the cell0 to keep testing easy
		targets.remove(cell0);

		// make cell2 a room
		cell2.setCard(room1);
		cell2.setRoomCenter(true);
		// add it to the targets
		targets.add(cell2);

		// Select a target
		BoardCell selected = testPlayer.selectTarget(targets);

		// Assert that it chooses the room
		assertEquals(selected, cell2);

		// have the player see the room
		testPlayer.updateSeen(room1);

		// test that it is randomly selecting between cell1 and cell2 now that it has
		// seen the room cell2
		num1 = 0;
		int num2 = 0;
		for (int i = 0; i < numTests; ++i) {
			selected = testPlayer.selectTarget(targets);
			if (selected.getCol() == 1) {
				num1++;
			} else if (selected.getCol() == 2) {
				num2++;
			} else {
				fail();
			}

		}

		// Assert that the choice is approximately random
		assertTrue(java.lang.Math.abs(num1 - num2) < numTests / 2);

	}
}
