package gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;

public class CardPanel extends JPanel {

	private JPKnownCards People, Weapons, Rooms;
	private HumanPlayer player;

	public CardPanel(HumanPlayer player) {
		// Set up the player gui, by supplying the human player object
		this.player = player;

		// Set the borders
		this.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		this.setLayout(new GridLayout(3, 0));

		// There are three different types of cards, thus we will construct three
		// different instances of a customized JPanels
		People = new JPKnownCards("People", CardType.PERSON, player);
		Weapons = new JPKnownCards("Weapons", CardType.WEAPON, player);
		Rooms = new JPKnownCards("Rooms", CardType.ROOM, player);

		// Add the things
		this.add(People);
		this.add(Rooms);
		this.add(Weapons);

	}

	// Update the seen cards
	public void updateSeen(Card card, Color color) {
		// remove them all
		this.removeAll();
		// Choose the type and update the respective cell
		if (card.getCardType() == CardType.PERSON) {
			People.updateSeen(card, color);

		} else if (card.getCardType() == CardType.ROOM) {
			Rooms.updateSeen(card, color);
		} else {
			Weapons.updateSeen(card, color);
		}
		// read all the cells and revalidate and repaint
		this.add(People);
		this.add(Rooms);
		this.add(Weapons);

		this.player.updateSeen(card);

		this.revalidate();
		this.repaint();

	}
}
