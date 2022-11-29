package gui;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;
import clueGame.Solution;

public class SuggestionDialog extends JDialog {
	private Board board;
	private Player suggestor;

	private JTextField currRoom;
	private JComboBox<Card> personCombo;
	private JComboBox<Card> weaponCombo;
	private Card roomCard;
	private Card personCard;
	private Card weaponCard;

	private Card suggestedRoom = null;
	private Card suggestedPerson = null;
	private Card suggestedWeapon = null;

	private JButton submit, cancel;

	public SuggestionDialog(Player player, Board board) {
		super(ClueGame.getInstance(), Dialog.ModalityType.DOCUMENT_MODAL);
		this.suggestor = player;
		this.board = board;

		suggestedRoom = player.getLocation().getCard();

		this.setLayout(new GridLayout(4, 2));

		JLabel roomLabel = new JLabel("Room");
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");

		currRoom = new JTextField();
		currRoom.setText(suggestedRoom.getName());
		currRoom.setEditable(false);

		personCombo = createComboBox(board.getPlayerCards());
		weaponCombo = createComboBox(board.getWeaponCards());

		submit = new JButton("Submit");
		cancel = new JButton("Cancel");

		// Add the combo Boxes
		this.add(roomLabel);
		this.add(currRoom);

		this.add(personLabel);
		this.add(personCombo);

		this.add(weaponLabel);
		this.add(weaponCombo);

		// Add the buttons
		this.add(submit);
		this.add(cancel);

		// Add the action Listeners

		ComboListener listener = new ComboListener();

		personCombo.addActionListener(listener);
		weaponCombo.addActionListener(listener);

	}

	public JComboBox<Card> createComboBox(Set<Card> cards) {
		JComboBox<Card> combo = new JComboBox<Card>();
		for (Card card : cards) {
			combo.addItem(card);
		}

		return combo;
	}

	private class ComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == personCombo) {
				suggestedPerson = (Card) personCombo.getSelectedItem();
				System.out.println(suggestedPerson);

			} else if (e.getSource() == weaponCombo) {
				suggestedWeapon = (Card) weaponCombo.getSelectedItem();
				System.out.println(suggestedWeapon);

			}
		}
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == cancel) {
				// Close the Jframe
			}

			else if (e.getSource() == submit) {
				// Ensure that all three Cards have been selected (none of them are null)

				// If one of them hasn't been selected (i.e it is null)
				if (suggestedRoom == null | suggestedPerson == null | suggestedWeapon == null) {
					JOptionPane splash = new JOptionPane();
					JFrame splashFrame = new JFrame();

					splash.showMessageDialog(splashFrame, "Please complete your card selction for the suggestion",
							"Welcome to ClueCar", JOptionPane.INFORMATION_MESSAGE);
				} else {
					// else the cards for the suggestion have been chosen
					Solution sugggestion = new Solution(suggestedPerson, suggestedWeapon, suggestedRoom);

					// Handle the suggestion
					board.handleSuggestion(sugggestion, suggestor);

				}

			}

		}

	}

}
