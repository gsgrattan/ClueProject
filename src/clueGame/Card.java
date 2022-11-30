package clueGame;

import java.awt.Color;
import java.util.Objects;

public class Card {
	// attributes
	private String cardName;
	private CardType type;
	private Color color;

	// constructor
	public Card(String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
	}

	public Card(String cardName, CardType type, Color color) {
		this.cardName = cardName;
		this.type = type;
		this.color = color;
	}

	public boolean equals(Card target) {
		// Compare the strings values (Not addresses)
		return (this.cardName.equals(target.cardName));
	}

	public CardType getCardType() {
		return this.type;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.cardName;
	}

	public Color getColor() {
		return this.color;
	}

	@Override
	public String toString() {
		return cardName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cardName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		return Objects.equals(cardName, other.cardName);
	}

}
