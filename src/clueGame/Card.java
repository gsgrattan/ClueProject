package clueGame;

public class Card {
	// attributes
	private String cardName;
	private CardType type;

	// constructor
	public Card(String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
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

	@Override
	public String toString() {
		return cardName;
	}

}
