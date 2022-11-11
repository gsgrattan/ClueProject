package gui;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
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

	private ArrayList<JTextField> seenCards;
	private ArrayList<JTextField> handCards;

	private final static int TEXT_FIELD_LENGTH = 10;

	public JPKnownCards(String string, CardType type, HumanPlayer player) {

		this.player = player;
		this.type = type;
		this.seenCards = new ArrayList<JTextField>();
		this.handCards = new ArrayList<JTextField>();

		this.setBorder(new TitledBorder(new EtchedBorder(), string));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.handPanel = new JPCardList("In Hand: ");
		this.seenPanel = new JPCardList("Seen: ");

		initHand();

		this.add(handPanel);
		this.add(seenPanel);

	}

	public void updateSeen(Card card, Color color) {
		this.seenPanel.updatePanel(card, color);
	}

	private void initHand() {
		for (Card card : player.getHand()) {
			if (card.getCardType() == this.type) {
				this.handPanel.updatePanel(card, Color.white);
			}
		}

	}
}
