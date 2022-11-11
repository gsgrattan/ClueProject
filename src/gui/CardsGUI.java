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
		this.player = player;
		this.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		this.setLayout(new GridLayout(3, 0));

		People = new JPKnownCards("People", CardType.PERSON, player);
		Weapons = new JPKnownCards("Weapons", CardType.WEAPON, player);
		Rooms = new JPKnownCards("Rooms", CardType.ROOM, player);

		this.add(People);
		this.add(Rooms);
		this.add(Weapons);

	}

	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		board.deal();
		// The first player in the arraylist should be the human player
		HumanPlayer player = board.getHumanPlayer();

		CardsGUI panel = new CardsGUI(player);
		JFrame frame = new JFrame();

		frame.setContentPane(panel);

		frame.setSize(180, 750); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); //

		Set<Card> deck = board.getDeck();
//		for (Card card : deck) {
//			panel.updateSeen(card, Color.orange);
//
//		}

	}

	public void updateSeen(Card card, Color color) {
		if (card.getCardType() == CardType.PERSON) {
			People.updateSeen(card, color);

		} else if (card.getCardType() == CardType.ROOM) {
			Rooms.updateSeen(card, color);
		} else {
			Weapons.updateSeen(card, color);
		}
	}

}
