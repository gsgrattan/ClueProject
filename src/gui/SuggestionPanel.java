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

public class SuggestionPanel extends JPanel {
	private Board board;
	private JTextField currRoom;
	private JComboBox<String> personCombo;
	private JComboBox<String> weaponsCombo;
	private Card roomCard;
	private Card personCard;
	private Card weaponCard;

	private JButton submit, cancel;

	public SuggestionPanel(Player player, Board board) {

		String roomName = player.getLocation().getCard().getName();

		this.setLayout(new GridLayout(4, 2));

		JLabel roomLabel = new JLabel("Room");
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");

		currRoom = new JTextField();
		currRoom.setText(roomName);
		currRoom.setEditable(false);

		personCombo = createComboBox(board.getPlayerCards());
		System.out.println(board.getPlayerCards().size());
		weaponsCombo = createComboBox(board.getWeaponCards());

		submit = new JButton("Submit");
		cancel = new JButton("Cancel");

		this.add(roomLabel);
		this.add(currRoom);

		this.add(personLabel);
		this.add(personCombo);

		this.add(weaponLabel);
		this.add(weaponsCombo);

		this.add(submit);
		this.add(cancel);

	}

	public JComboBox<String> createComboBox(Set<Card> cards) {
		JComboBox<String> womboCombo = new JComboBox<String>();
		System.out.println(cards.size());
		for (Card card : cards) {
			System.out.println(card.getName());
			womboCombo.addItem(card.getName());
		}

		return womboCombo;
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
