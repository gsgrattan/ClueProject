package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;

public class CardsGUI extends JPanel {

	private JPKnownCards People, Weapons, Rooms;
	private HumanPlayer player;

	public CardsGUI(HumanPlayer player) {
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

	public static void main(String[] args) {

		// Get the instance of the board and load in the
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		board.deal();

		// The first player in the arraylist should be the human player
		HumanPlayer player = board.getHumanPlayer();

		// Create the GUI instance and the JFRAME to display it
		CardsGUI panel = new CardsGUI(player);
		JFrame frame = new JFrame();

		// add the content and display it
		frame.setContentPane(panel);

		frame.setSize(180, 750); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); //

		// get the deck to load in the cards
		Set<Card> deck = board.getDeck();

		// Get the size and an index iterator
		int size = deck.size();
		int i = 0;

		// iterate through the deck
		for (Card card : deck) {
			Color color;

			// if even make orange, if odd make blue
			if (i % 2 == 0) {
				color = Color.orange;
			} else {
				color = Color.blue;

			}
			// updatethe seen
			panel.updateSeen(card, color);

			// just to make sure we don't print all the cards
			i++;
			if (i > size / 2) {
				break;
			}
		}
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
		// readd all the cells and revalidate and repaint
		this.add(People);
		this.add(Rooms);
		this.add(Weapons);

		this.revalidate();
		this.repaint();
	}
}
