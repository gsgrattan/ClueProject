package clueGame;

public class Card {
	private String cardName;
	private CardType type;

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

}
