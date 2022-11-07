package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	// attributes
	private String name;
	private Color color;
	private int row;
	private int col;
	private ArrayList<Card> hand;
	private Set<Card> seenCards;

	// constructor
	protected Player(String name) {

		this.name = name;
		this.hand = new ArrayList<Card>();

		seenCards = new HashSet<Card>();

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

	public Card disproveSuggestion(Solution suggestion) {

		return new Card(null, null);
	}

}
