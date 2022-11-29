package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;

public class AccusationPanel extends JPanel {
	private Board board;
	private JTextField currRoom;
	private JComboBox<String> roomCombo;
	private JComboBox<String> personCombo;
	private JComboBox<String> weaponsCombo;
	private Card roomCard;
	private Card personCard;
	private Card weaponCard;

	private JButton submit, cancel;

	public AccusationPanel(Player player, Board board) {
		this.board = board;

		String roomName = player.getLocation().getCard().getName();

		this.setLayout(new GridLayout(4, 2));

		JLabel roomLabel = new JLabel("Room");
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");

		roomCombo = createComboBox(board.getRoomCards());
		personCombo = createComboBox(board.getPlayerCards());
		weaponsCombo = createComboBox(board.getWeaponCards());

		submit = new JButton("Submit");
		cancel = new JButton("Cancel");

		this.add(roomLabel);
		this.add(roomCombo);

		this.add(personLabel);
		this.add(personCombo);

		this.add(weaponLabel);
		this.add(weaponsCombo);

		this.add(submit);
		this.add(cancel);

	}

	public JComboBox<String> createComboBox(Set<Card> cards) {
		JComboBox<String> combo = new JComboBox<String>();
		System.out.println(cards.size());
		for (Card card : cards) {
			System.out.println(card.getName());
			combo.addItem(card.getName());
		}

		return combo;
	}

	private class ComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == weaponsCombo) {

			} else if (e.getSource() == personCombo) {

			}
		}
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}

	}
}
