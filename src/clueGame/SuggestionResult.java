package clueGame;

public class SuggestionResult {
	private Card card;
	private Player person;

	public SuggestionResult(Card card, Player person) {
		this.card = card;
		this.person = person;
	}

	public Card getCard() {
		return card;
	}

	public Player getPerson() {
		return person;
	}

}
