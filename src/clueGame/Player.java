package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	// attributes
	private String name;
	private Color color;

	private BoardCell location;

	private ArrayList<Card> hand;
	private Set<Card> seenCards;

	private Set<Card> seenPeopleCards;
	private Set<Card> seenWeaponCards;
	private Set<Card> seenRoomCards;

	// constructor
	protected Player(String name) {

		this.name = name;
		this.hand = new ArrayList<Card>();
		seenCards = new HashSet<Card>();

	}

	public Card disproveSuggestion(Solution suggestion, Player suggestor) {
		// TODO: Refactor ans clean up method

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

}
