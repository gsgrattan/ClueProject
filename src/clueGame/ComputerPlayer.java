package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	// constructor

	boolean knowsSolution = false;

	public ComputerPlayer(String name, Board board, BoardCell boardcell, Color color) {
		super(name, board, boardcell, color);
	}

	// Create a random suggestion based off the not seen cards.
	public Solution createSuggestion() {

		BoardCell location = this.getLocation();

		// Always null if we are not at a room center.
		Card roomCard = location.getCard();

		Board board = this.getBoard();

		Set<Card> possiblePlayerCards = new HashSet<Card>(board.getPlayerCards());
		Set<Card> possibleWeaponCards = new HashSet<Card>(board.getWeaponCards());

		possiblePlayerCards.removeAll(this.getSeenPeopleCards());
		possibleWeaponCards.removeAll(this.getSeenWeaponCards());

		int item1 = new Random().nextInt(possiblePlayerCards.size());
		int item2 = new Random().nextInt(possibleWeaponCards.size());

		Card chosenPlayerCard = null;
		Card chosenWeaponCard = null;

		int i = 0;
		for (Card c : possiblePlayerCards) {
			if (i == item1) {
				chosenPlayerCard = c;
			}
			i++;
		}

		i = 0;
		for (Card c : possibleWeaponCards) {
			if (i == item2) {
				chosenWeaponCard = c;
			}
			i++;
		}

		return new Solution(chosenPlayerCard, chosenWeaponCard, roomCard);

	}

	public boolean knowsSolution() {
		// The computer knows the solution if they haven't seen only one Player, Weapon,
		// or Card

		if (knowsSolution) {
			return knowsSolution;
		}

		boolean result;

		Board board = this.getBoard();
		Set<Card> possiblePlayerCards = new HashSet<Card>(board.getPlayerCards());
		Set<Card> possibleRoomCards = new HashSet<Card>(board.getRoomCards());
		Set<Card> possibleWeaponCards = new HashSet<Card>(board.getWeaponCards());

		possiblePlayerCards.removeAll(this.getSeenPeopleCards());
		possibleRoomCards.removeAll(this.getSeenRoomCards());
		possibleWeaponCards.removeAll(this.getSeenWeaponCards());

		// If the size of each the possible card lists is one, there
		result = ((possiblePlayerCards.size() == 1) & (possibleRoomCards.size() == 1)
				& (possibleWeaponCards.size() == 1));

		return result;

	}

	// Select a target based off the targets generated by the board class. Will
	// always visit any room in the targets set unless that room has previously been
	// visited.
	// TODO later, not in any assignment yet. If the room is not in the list and we
	// want to make a suggestion then we need to
	public BoardCell selectTarget(Set<BoardCell> targets) {

		int item = new Random().nextInt(targets.size());

		BoardCell randomCell = null;
		BoardCell roomCell = null;

		int i = 0;
		for (BoardCell b : targets) {
			if (i == item) {
				randomCell = b;
			}
			i++;
			if (b.isRoomCenter()) {
				roomCell = b;
			}
		}

		if (roomCell != null && !this.getSeenRoomCards().contains(roomCell.getCard())) {
			return roomCell;
		} else {
			return randomCell;
		}
	}

	public void setKnowsSolution() {
		this.knowsSolution = true;
	}

}
