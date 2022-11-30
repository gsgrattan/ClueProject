package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	// attributes
	private String name;
	private Color color;

	private BoardCell location;
	private boolean movedAgainstWill;

	private ArrayList<Card> hand;
	private Set<Card> seenCards;

	private Set<Card> seenPeopleCards;
	private Set<Card> seenWeaponCards;
	private Set<Card> seenRoomCards;

	private Board board;

	private int playerNum;

	private boolean hasMoved = false;

	// constructor
	protected Player(String name, Board board, BoardCell location, Color color) {

		this.name = name;
		this.board = board;
		this.color = color;
		this.location = location;
		this.hand = new ArrayList<Card>();
		seenCards = new HashSet<Card>();
		seenPeopleCards = new HashSet<Card>();
		seenWeaponCards = new HashSet<Card>();
		seenRoomCards = new HashSet<Card>();

	}

	public Card disproveSuggestion(Solution suggestion, Player suggestor) {
		// TODO: Refactor and clean up method

		ArrayList<Card> proofList = new ArrayList();

		// if this player made the accusation, return null;
		if (this.name.equals(suggestor.getName())) {
			return null;
		} else {
			Card person = suggestion.getPerson();
			Card weapon = suggestion.getWeapon();
			Card room = suggestion.getRoom();

			// Check the overlap (if any) between the Solution and the players hand
			if (hand.contains(person)) {
				proofList.add(person);
			}
			if (hand.contains(weapon)) {
				proofList.add(weapon);
			}
			if (hand.contains(room)) {
				proofList.add(room);
			}

			if (proofList.size() == 0) {
				return null;
			} else {
				Random rand = new Random();

				int randInt = rand.nextInt(proofList.size());

				return proofList.get(randInt);

			}
		}

	}

	public void updateHand(Card card) {
		this.hand.add(card);
	}

	public void updateSeen(Card seenCard) {
		seenCards.add(seenCard);
		if (seenCard.getCardType() == CardType.ROOM) {
			this.seenRoomCards.add(seenCard);
		} else if (seenCard.getCardType() == CardType.WEAPON) {
			this.seenWeaponCards.add(seenCard);
		} else if (seenCard.getCardType() == CardType.PERSON) {
			this.seenPeopleCards.add(seenCard);
		}
	}

	// Draws the player
	public void drawPlayer(Graphics g, int cellWidth, int cellHeight) {
		int x = cellWidth * this.location.getCol();
		int y = cellHeight * this.location.getRow();

		// if the current player is in a room
		if (this.getLocation().isRoomCenter()) {

			// Add an offset based on their player number to ensure that players do not
			// overlap in a room
			x += (cellWidth * playerNum / 2);

		}

		// Paint the player and the border
		g.setColor(this.getColor());
		g.fillOval(x, y, cellWidth, cellHeight);

		// Draw the black border
		g.setColor(Color.black);
		g.drawOval(x, y, cellWidth, cellHeight);
	}

	// Getters and setters
	public int getHandSize() {
		return hand.size();
	}

	public String getName() {
		return this.name;
	}

	public void setLocation(BoardCell newLocation) {
		this.location = newLocation;
	}

	public BoardCell getLocation() {
		return this.location;
	}

	public Board getBoard() {
		return board;
	}

	public Set<Card> getSeenPeopleCards() {
		return seenPeopleCards;
	}

	public Set<Card> getSeenWeaponCards() {
		return seenWeaponCards;
	}

	public Set<Card> getSeenRoomCards() {
		return seenRoomCards;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public ArrayList<Card> getHand() {
		// TODO Auto-generated method stub
		return this.hand;
	}

	public void setPlayerNum(int num) {
		this.playerNum = num;
	}

	public int getPlayerNum() {
		return this.playerNum;

	}

	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;

	}

	public boolean getHasMoved() {
		return this.hasMoved;
	}

	public void setMovedAgainstWill(boolean value) {
		this.movedAgainstWill = value;
	}

	public boolean getMovedAgainstWill() {
		return this.movedAgainstWill;
	}

	public void move(BoardCell move) {
		this.getLocation().setOccupied(false);
		move.setOccupied(true);
		this.setLocation(move);
//		if (move.isRoomCenter()) {
//
//			this.updateSeen(move.getCard());
//		}

		if (!this.movedAgainstWill) {
			this.hasMoved = true;
		}
	}
}
