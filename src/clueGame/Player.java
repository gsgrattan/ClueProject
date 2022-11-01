package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private Color color;
	private int row;
	private int col;
	private ArrayList<Card> hand;

	public void updateHand(Card card) {
		this.hand.add(card);
	}

}
