package gui;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;

public class JPKnownCards extends JPanel {
	private JPCardList handPanel, seenPanel;

	private Player player;
	private CardType type;

	private final static int TEXT_FIELD_LENGTH = 10;

	public JPKnownCards(String string, CardType type, HumanPlayer player) {

		// Get the player object and card type for this cell
		this.player = player;
		this.type = type;

		// Set the border and the layout
		this.setBorder(new TitledBorder(new EtchedBorder(), string));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Create the two panels for the hand and seen
		this.handPanel = new JPCardList("In Hand: ");
		this.seenPanel = new JPCardList("Seen: ");

		// initialize the hand
		initHand();

		// add the subpanels
		this.add(handPanel);
		this.add(seenPanel);
	}

	public void updateSeen(Card card, Color color) {
		// if the card is not in the hand, add it
		if (!player.getHand().contains(card)) {

			this.seenPanel.updatePanel(card, color);
			this.add(handPanel);
			this.add(seenPanel);

		}
	}

	private void initHand() {
		// initialize the hand
		for (Card card : player.getHand()) {
			if (card.getCardType() == this.type) {
				this.handPanel.updatePanel(card, Color.white);
			}
		}

	}
}
