package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class Player {
	private String name;
	private Color color;
	private int row;
	private int col;
	private ArrayList<Card> hand;
	private HashSet<Card> cardsSeen;

	protected Player(String name) {
		this.name = name;
		this.hand = new ArrayList<Card>();

	}

	public void updateHand(Card card) {
		this.hand.add(card);
	}

	public int getHandSize() {
		return hand.size();
	}

	public void updateSeen(Card seenCard) {
		cardsSeen.add(seenCard);
	}

	public String getName() {
		return this.name;
	}

}
